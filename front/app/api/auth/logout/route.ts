import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function GET() {
    (await cookies()).set("backend_token", "", {
        httpOnly: true,
        secure: !!process.env.NEXTAUTH_SECRET,
        sameSite: "lax",
        path: "/",
        maxAge: 0,
    });

    return NextResponse.json({ success: true });
}