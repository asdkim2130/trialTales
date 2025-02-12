"use client";

import { useState } from "react";
import Link from "next/link";

//제출폼
export function ApplicationRequest({ submit }: { submit: (formData: FormData) => Promise<any> }) {


    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [isSubmitted, setIsSubmitted] = useState(false);

    async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setLoading(true);

        const formData = new FormData(event.currentTarget);

        const response = await submit(formData); // 서버 액션 호출

        setLoading(false);
        setMessage(response.message);
        setIsSubmitted(true);
    }

    return (
        <div className="p-6 max-w-[500px] mx-auto bg-white rounded-lg">
            <h2 className="text-2xl font-semibold text-gray-800 mb-6 text-center">
                체험단 신청하기
            </h2>
            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label className="block text-sm font-medium text-gray-700">
                        E-mail
                    </label>
                    <input
                        type="email"
                        name="email"
                        required
                        placeholder="이메일"
                        className="mt-1 p-2 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>
                <div>
                    <label className="block text-sm font-medium text-gray-700">
                        SNS 계정
                    </label>
                    <input
                        type="text"
                        name="snsUrl"
                        placeholder="SNS 계정 링크"
                        className="mt-1 p-2 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>
                <div>
                    {isSubmitted ? (
                        <Link href="/campaigns">
                            <button
                                type="button"
                                className="w-full mt-4 bg-green-500 text-white py-2 px-4 rounded-md hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-500"
                            >
                                홈으로
                            </button>
                        </Link>
                    ) : (
                    <button
                        type="submit"
                        className="w-full mt-4 bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        disabled={loading}
                    >
                        {loading ? "Submitting..." : "제출"}
                    </button>
                        )}
                </div>
                {message && <p className="mt-4 text-center text-gray-600">{message}</p>}
            </form>
        </div>
    );
}

