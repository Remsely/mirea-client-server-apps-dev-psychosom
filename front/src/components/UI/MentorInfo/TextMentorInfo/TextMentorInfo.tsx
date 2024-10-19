interface TextMainProps {
    children : string
}

export default function TextMentorInfo({children} : TextMainProps) {
    return (
        <p>{children}</p>
    )
}
