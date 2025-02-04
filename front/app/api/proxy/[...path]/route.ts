import {NextRequest, NextResponse} from "next/server";
import {cookies} from "next/headers";

export const dynamic = 'force-dynamic';

async function handler(req: NextRequest, { params }: { params: { path: string[] } })  {
    try {
        const token = (await cookies()).get("backend_token")?.value;
        if (!token) return new NextResponse("Unauthorized", {status: 401});

        const pathSegment = params.path.join("/");
        const backendUrl = new URL(`${process.env.BACKEND_URL}/${pathSegment}${req.nextUrl.search}`);

        const res = await fetch(backendUrl, {
            method: req.method,
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
            cache: "no-store",
            body: req.method !== "GET" ? JSON.stringify(await req.json()) : undefined,
        });

        const data = await res.json();
        return NextResponse.json(data);

    } catch (error) {
        console.error("Proxy error:", error);
        return new NextResponse("Internal Server Error", {status: 500});
    }
}

export const GET = handler;
export const POST = handler;
export const PUT = handler;