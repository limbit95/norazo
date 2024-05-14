document.querySelector("#boardWrite")
.addEventListener("submit", e => {

    const boardTitle = document.querySelector("[name='boardTitle']");
    const boardContent = document.querySelector("[name='boardContent']");

    //enter => <br>
var text = document.getElementById("textarea").value;
text = text.replace(/(?:\r\n|\r|\n)/g, '<br>');

//<br> => enter
var text = document.getElementById("textarea").value;
text = text.replaceAll("<br>", "\r\n");
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

  });

  