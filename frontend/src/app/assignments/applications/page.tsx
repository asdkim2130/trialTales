export default function ApplicationPage() {

    interface Campaign {
        id: number;
        campaignName: string;
        description: string;
        startDate: string;
        endDate: string;
        status: string;
        recruitmentLimit: number;
        deleted: boolean;
    }



    async function submit(formData: FormData) {
        "use server";

        const rawFormData = {
            email: formData.get("email"),
            snsUrl: formData.get("snsUrl"),
        };

        console.log({ ...rawFormData });
    }

    return (
        <>
               <form action={submit}>
                <label> e-mail. </label>
                <input
                    type="text"
                    name="email"
                    required
                    placeholder="이메일"
                    className="border border-black"
                />
                <br></br>
                <label> snsUrl </label>
                <input
                    type="text"
                    name="snsUral"
                    placeholder="sns계정"
                    className="border border-black"
                />
                <br></br>
                <button>제출</button>
            </form>
        </>
    );
}