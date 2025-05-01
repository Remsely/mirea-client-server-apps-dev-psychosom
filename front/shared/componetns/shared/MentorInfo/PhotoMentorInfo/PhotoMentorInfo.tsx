import React from "react";
import "./PhotoMentorInfo.scss";
import {PsychologistProfileType} from "@/@types/types";

export function PhotoMentorInfo({specialist} : PsychologistProfileType) {
    return (
        <div className="psychologist-profile__photo-block">
            <img
                className="psychologist-profile__photo"
                src={specialist.profileImage}
                alt={`${specialist.firstName} ${specialist.lastName}`}
            />
        </div>
    )
}