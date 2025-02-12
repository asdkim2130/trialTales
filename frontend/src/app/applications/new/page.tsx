import ClientPage from "@/app/applications/new/clientComponent";
import UserSubmit from "@/app/applications/new/handleSubmit";


export default function ApplicationPage() {
    return (
        <div className="bg-white rounded-lg shadow-lg overflow-hidden max-w-[1000px] mx-auto flex min-h-screen items-center">
            <div className="w-1/2 p-6 flex flex-col">
            <ClientPage />
            </div>
            <div className="w-1/2 p-6 border-l">
            <UserSubmit />
            </div>
        </div>
    )
}