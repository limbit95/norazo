// 제출 시 유효성 검사
const boardUpdateFrm = document.querySelector("#boardUpdateFrm");

boardUpdateFrm.addEventListener("submit", e => {

  const boardTitle = document.querySelector("[name='boardTitle']");
  const boardContent = document.querySelector("[name='boardContent']");

  if(boardTitle.value.trim().length == 0){
    alert("제목을 작성해 주세요");
    boardTitle.focus();
    e.preventDefault();
    return;
  }

  if(boardContent.value.trim().length == 0){
    alert("내용을 작성해 주세요");
    boardContent.focus();
    e.preventDefault();
    return;
  }

  if(loginMember != null) {
    let text = boardContent.value;
    const introduced = text.replaceAll(/\n/g, "<br>");
    if (introduced != null) {
        document.getElementById('introduced').innerHTML = introduced;
    }};
  const introduce = boardContent.value.replaceAll("<br>", "\r\n");
  if( document.getElementById('introduce') != null) {
    if (introduce != null) {
        document.getElementById('introduce').innerHTML = introduce;
    }
  }


});