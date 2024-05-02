const categoryBtn = document.querySelector(".category-btn");
const logo = document.querySelector(".logo");
const myPageBtn = document.querySelector(".myPage-btn");

categoryBtn.addEventListener("click", () => {
    location.href = "/category";
});

logo.addEventListener("click", () => {
    location.href = "/";
});

myPageBtn.addEventListener("click", () => {
    if(loginMember == null){
        alert("로그인 후 이용해주세요");
        location.href = "/login";
        return;
    }

    location.href = "/myPage/main";
});

