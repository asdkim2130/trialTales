import Link from 'next/link';

export default function Home() {
  return (
      <div>
        <Link href="members/login">로그인</Link>
        <Link href="members/profile">내 프로필</Link>
      </div>
  );
}
