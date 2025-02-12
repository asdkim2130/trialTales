import { useState, useEffect } from "react";
import { useRouter, usePathname } from "next/navigation";
import styles from "./navbar.module.css";

export default function Navbar() {
    const router = useRouter();
    const pathname = usePathname();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    // 로그인 상태 확인 (새로고침해도 유지됨)
    useEffect(() => {
        const user = localStorage.getItem("user");
        if (user) {
            setIsLoggedIn(true);
        } else {
            setIsLoggedIn(false);
        }
    }, []);

    // 로그아웃 기능
    const handleLogout = () => {
        localStorage.removeItem("user"); // 유저 정보 삭제
        setIsLoggedIn(false);
        router.push("/campaigns"); // 로그아웃 후 campaigns 페이지로 이동
    };

    return (
        <nav className={styles.navbar}>
            <div className={styles.logo} onClick={() => router.push("/campaigns")}>
                🚀 trial Tales
            </div>
            <ul className={styles.menu}>
                {!isLoggedIn ? (
                    // 로그인 상태가 아니면 로그인 버튼 표시
                    <li>
                        <button
                            className={`${styles.navButton} ${pathname === "/members/login" ? styles.active : ""}`}
                            onClick={() => router.push("/members/login")}
                        >
                            로그인
                        </button>
                    </li>
                ) : (
                    // 로그인 상태면 프로필 + 로그아웃 버튼 표시
                    <>
                        <li>
                            <button
                                className={`${styles.navButton} ${pathname === "/members/profile" ? styles.active : ""}`}
                                onClick={() => router.push("/members/profile")}
                            >
                                프로필
                            </button>
                        </li>
                        <li>
                            <button className={styles.navButton} onClick={handleLogout}>
                                로그아웃
                            </button>
                        </li>
                    </>
                )}
            </ul>
        </nav>
    );
}
