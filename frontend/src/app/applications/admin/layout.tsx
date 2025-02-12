import Navbar from "./navbar";

export default function AdminLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div>
      <Navbar />
      <div className="p-6">{children}</div>
    </div>
  );
}
