interface TextMainProps {
    children : string
}

export function TextMentorInfo({children} : TextMainProps) {
    return (
        <p>{children}</p>
    )
}
