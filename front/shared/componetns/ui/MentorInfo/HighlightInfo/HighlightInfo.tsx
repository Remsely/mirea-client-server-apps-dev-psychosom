"use client";

import {ReactNode, useEffect} from 'react';
import styles from "./HighlightInfo.module.scss";

interface HighlightInfoProps {
    children: ReactNode;
    onLinkClick?: () => void;
}

export function HighlightInfo(props: HighlightInfoProps) {
    useEffect(() => {
        if (props.onLinkClick) {
            const link = document.querySelector('#important-link');
            if (link) {
                link.addEventListener('click', props.onLinkClick);
            }

            return () => {
                if (link && props.onLinkClick) {
                    link.removeEventListener('click', props.onLinkClick);
                }
            };
        }
    }, [props.onLinkClick]);

    return (
        <div className={styles.highlight}>
            <p>{props.children}</p>
        </div>
    );
}
