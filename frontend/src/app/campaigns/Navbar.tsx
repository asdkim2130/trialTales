import { useRouter, usePathname } from "next/navigation"; // ✅ 현재 경로 확인
import styles from "./navbar.module.css";

export default function Navbar() {
    const router = useRouter();
    const pathname = usePathname(); // ✅ 현재 URL 가져오기

    return (
        <nav className={styles.navbar}>
            <div className={styles.logo} onClick={() => router.push("/campaigns")}>
                🚀 trial Tales
            </div>
            <ul className={styles.menu}>
                <li>
                    <button
                        className={`${styles.navButton} ${pathname === "/" ? styles.active : ""}`}
                        onClick={() => router.push("/campaigns")}
                    >
                        로그인
                    </button>
                </li>
                <li>
                    <button
                        className={`${styles.navButton} ${pathname === "/campaigns" ? styles.active : ""}`}
                        onClick={() => router.push("/campaigns")}
                    >
                        프로필
                    </button>
                </li>
            </ul>
        </nav>
    );
}
