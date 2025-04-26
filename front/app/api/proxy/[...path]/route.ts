import { NextRequest, NextResponse } from "next/server";
import { cookies } from "next/headers";

export const dynamic = 'force-dynamic';

async function handler(req: NextRequest, { params }: { params: Promise<{ path: string[] }>}) {
    const { path } = await params;
    try {
        const token = (await cookies()).get("backend_token")?.value;
        if (!token) return new NextResponse("Unauthorized", { status: 401 });

        const pathSegment = path.join("/");
        const backendUrl = new URL(`${process.env.BACKEND_URL}/${pathSegment}${req.nextUrl.search}`);

        let requestBody;
        if (req.method !== "GET") {
            const contentLength = req.headers.get('content-length');
            if (contentLength && parseInt(contentLength) > 0) {
                requestBody = await req.json();
            }
        }

        const res = await fetch(backendUrl, {
            method: req.method,
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
            cache: "no-store",
            body: requestBody ? JSON.stringify(requestBody) : undefined,
        });

        const contentType = res.headers.get("content-type") || "";
        let responseBody: BodyInit;
        if (contentType.includes("application/json")) {
            responseBody = await res.json();
            return NextResponse.json(responseBody, { status: res.status });
        } else {
            responseBody = await res.text();
            return new NextResponse(responseBody, {
                status: res.status,
                headers: { "content-type": contentType }
            });
        }
    } catch (error) {
        console.error("Proxy error:", error);
        return new NextResponse("Internal Server Error", { status: 500 });
    }
}

export const GET = handler;
export const POST = handler;
export const PUT = handler;