import { NextRequest, NextResponse } from 'next/server'
import { cookies } from 'next/headers'

export const dynamic = 'force-dynamic'

type ParamsObj = Record<string, string | string[]>

const toParamsObject = (sp: URLSearchParams): ParamsObj =>
    Array.from(sp.entries()).reduce<ParamsObj>((acc, [k, v]) => {
        const prev = acc[k]
        acc[k] = prev === undefined ? v : Array.isArray(prev) ? [...prev, v] : [prev, v]
        return acc
    }, {})

const METHOD_MAP = {
    GET: {name: 'get', hasBody: false},
    DELETE: {name: 'delete', hasBody: false},
    POST: {name: 'post', hasBody: true},
    PUT: {name: 'put', hasBody: true},
    PATCH: {name: 'patch', hasBody: true}
} as const

async function handler(
    req: NextRequest,
    { params }: { params: Promise<{ path: string[] }> }
) {
    const token = (await cookies()).get('backend_token')?.value
    const { path } = await params
    const endpoint = path.join('/')

    const queryParams = toParamsObject(req.nextUrl.searchParams)
    const methodCfg = METHOD_MAP[req.method as keyof typeof METHOD_MAP]
    if (!methodCfg) return new NextResponse('Method Not Allowed', { status: 405 })

    const rawBody = methodCfg.hasBody ? await req.clone().text() : undefined
    const body =
        methodCfg.hasBody && rawBody && rawBody.length > 0
            ? JSON.parse(rawBody)
            : undefined

    try {
        const url = new URL(`${process.env.BACKEND_URL}/${endpoint}`)
        Object.entries(queryParams).forEach(([k, v]) => {
            if (Array.isArray(v)) v.forEach(val => url.searchParams.append(k, val))
            else url.searchParams.set(k, v)
        })

        const headers: Record<string, string> = {
            'Content-Type': 'application/json'
        }
        if (token) {
            headers['Authorization'] = `Bearer ${token}`
        }

        const backendRes = await fetch(url, {
            method: req.method,
            headers,
            cache: 'no-store',
            body: methodCfg.hasBody ? JSON.stringify(body ?? {}) : undefined
        })

        const ct = backendRes.headers.get('content-type') ?? ''
        const outBody = ct.includes('application/json')
            ? await backendRes.json()
            : await backendRes.text()

        return ct.includes('application/json')
            ? NextResponse.json(outBody, { status: backendRes.status })
            : new NextResponse(String(outBody), {
                status: backendRes.status,
                headers: { 'content-type': ct }
            })
    } catch (err) {
        console.error('Proxy error:', err)
        return new NextResponse('Internal Server Error', {status: 500})
    }
}

export const GET = handler
export const POST = handler
export const PUT = handler
export const PATCH = handler
export const DELETE = handler