const newMoim = document.querySelector(".newMoim");

newMoim.addEventListener("click", e => {
    if(loginMember == null){
        if(confirm("로그인 후 사용 가능한 기능입니다. \n로그인 페이지로 이동하시겠습니까?")){
            location.href = "/member/login";
            return;
        } else{
            return;
        }
    }
    location.href = "/editBoard/insert/" + boardTypeList[0].boardCode;
});