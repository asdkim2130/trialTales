"use client";

//use client 포장
import { ApplicationRequest } from "@/app/applications/new/requestComponent";
import { submitApplication } from "@/app/applications/new/submitComponent";


export default function UserSubmit() {

    async function handleSubmit(formData: FormData) {

        return await submitApplication(formData);
    }
    return (
        <div>
            <ApplicationRequest submit={submitApplication} />
        </div>
    );
}