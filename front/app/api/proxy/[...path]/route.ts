import {NextRequest, NextResponse} from 'next/server'
import {cookies} from 'next/headers'
import {FetchClient, FetchError} from '@/shared/utils'

export const dynamic = 'force-dynamic'

type ParamsObj = Record<string, string | string[]>

const toParamsObject = (sp: URLSearchParams): ParamsObj =>
    Array.from(sp.entries()).reduce<ParamsObj>((acc, [k, v]) => {
        const prev = acc[k]
        acc[k] = prev === undefined ? v : Array.isArray(prev) ? [...prev, v] : [prev, v]
        return acc
    }, {})

let clientInstance: FetchClient | null = null
const getClient = (token: string) =>
    (clientInstance ||= new FetchClient({
        baseUrl: process.env.BACKEND_URL ?? '',
        headers: {Authorization: `Bearer ${token}`}
    }))

const METHOD_MAP = {
    GET: {name: 'get', hasBody: false},
    DELETE: {name: 'delete', hasBody: false},
    POST: {name: 'post', hasBody: true},
    PUT: {name: 'put', hasBody: true},
    PATCH: {name: 'patch', hasBody: true}
} as const

async function handler(
    req: NextRequest,
    {params}: { params: Promise<{ path: string[] }> }
) {
    const token = (await cookies()).get('backend_token')?.value
    if (!token) return new NextResponse('Unauthorized', {status: 401})

    const client = getClient(token)
    const {path} = await params
    const endpoint = path.join('/')

    const queryParams = toParamsObject(req.nextUrl.searchParams)
    const methodCfg = METHOD_MAP[req.method as keyof typeof METHOD_MAP]
    if (!methodCfg) return new NextResponse('Method Not Allowed', {status: 405})

    const rawBody = methodCfg.hasBody ? await req.clone().text() : undefined
    const body =
        methodCfg.hasBody && rawBody && rawBody.length > 0
            ? JSON.parse(rawBody)
            : undefined
    try {
        /* eslint-disable  @typescript-eslint/no-explicit-any */
        const call = (client as any)[methodCfg.name].bind(client)
        const data = methodCfg.hasBody
            ? await call(endpoint, body, {params: queryParams})
            : await call(endpoint, {params: queryParams})

        const isJson = typeof data === 'object' || Array.isArray(data)
        return isJson
            ? NextResponse.json(data, {status: 200})
            : new NextResponse(String(data), {
                status: 200,
                headers: {'content-type': 'text/plain; charset=utf-8'}
            })
    } catch (err) {
        if (err instanceof FetchError) {
            try {
                const url = new URL(
                    `${process.env.BACKEND_URL}/${endpoint}`
                )
                Object.entries(queryParams).forEach(([k, v]) => {
                    if (Array.isArray(v)) v.forEach(val => url.searchParams.append(k, val))
                    else url.searchParams.set(k, v)
                })

                const backendRes = await fetch(url, {
                    method: req.method,
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    cache: 'no-store',
                    body: methodCfg.hasBody ? JSON.stringify(body ?? {}) : undefined
                })

                const ct = backendRes.headers.get('content-type') ?? ''
                const outBody = ct.includes('application/json')
                    ? await backendRes.json()
                    : await backendRes.text()

                return ct.includes('application/json')
                    ? NextResponse.json(outBody, {status: backendRes.status})
                    : new NextResponse(String(outBody), {
                        status: backendRes.status,
                        headers: {'content-type': ct}
                    })
                /* eslint-disable  @typescript-eslint/no-unused-vars */
            } catch (e) {
                return new NextResponse(err.message, {status: err.statusCode})
            }
        }

        console.error('Proxy error:', err)
        return new NextResponse('Internal Server Error', {status: 500})
    }
}

export const GET = handler
export const POST = handler
export const PUT = handler
export const PATCH = handler
export const DELETE = handler