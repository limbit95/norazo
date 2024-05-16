
/* REST(REpresentational State Transfer)  API

- 자원(데이터,파일)을 이름(주소)으로 
  구분(representational) 하여
  자원의 상태(State)를 주고 받는 것(Transfer)

 -> 자원의 이름(주소)를 명시하고
   HTTP Method(GET,POST,PUT,DELETE) 를 이용해
   지정된 자원에 대한 CRUD 진행

  자원의 이름(주소)는 하나만 지정 (ex. /comment)
   
  삽입 == POST    (Create)
  조회 == GET     (Read)
  수정 == PUT     (Update)
  삭제 == DELETE  (Delete)
*/

/* ***** 댓글 목록 조회(ajax) ***** */
const selectCommentList = () => {
  
  
  // [GET]
  // fetch(주소?쿼리스트링) 

  // [POST, PUT, DELETE]
  // fetch(주소, {method : "", header : {}, body : ""})

  // response.json() 
  // - 응답 받은 JSON 데이터 -> JS 객체로 변환

  fetch("/comment?boardNo=" + boardNo) // GET 방식 요청
  .then(response => response.json())
  .then(commentList => {
    // console.log(commentList);
    
    // 화면에 존재하는 기존 댓글 목록 삭제 후
    // 조회된 commentList를 이용해서 새로운 댓글 목록 출력
    
    // ul태그(댓글 목록 감싸는 요소)
    const boardCommentDivTop = document.querySelector(".boardCommentDivTop");
    // boardCommentDivTop.removeChild(boardCommentDivTop.childNodes); // 기존 댓글 목록 삭제
    for(let i = 0; i < boardCommentDivTop.childNodes.length;){
      boardCommentDivTop.removeChild(boardCommentDivTop.childNodes[i]);
    }
    
    /* ******* 조회된 commentList를 이용해 댓글 출력 ******* */
    for(let comment of commentList){
      
      const chatMainContainer = document.createElement("div");
      chatMainContainer.classList.add("chatMainContainer");

      // 대댓글(자식 댓글)인 경우 "child-comment" 클래스 추가
      if(comment.parentCommentNo != 0) 
        chatMainContainer.classList.add("child-comment");
      
      // 행(li) 생성 + 클래스 추가
      let chatContainer = document.createElement("div");
      if(loginMemberNo != comment.memberNo){
        chatContainer.classList.add("chatContainer");
      } else if(loginMemberNo == comment.memberNo){
        chatContainer.classList.remove("chatContainer");
        chatContainer.classList.add("chatContainerUser");
      }

      let chatContentContainer = document.createElement("div");
      if(loginMemberNo != comment.memberNo){
        chatContentContainer.classList.add("chatContentContainer");
      } else if(loginMemberNo == comment.memberNo){
        chatContentContainer.classList.remove("chatContentContainer");
        chatContentContainer.classList.add("chatContentContainerUser");
      }


      if(comment.commentDelFl == 'N'){ // 삭제되지 않은 댓글

        // 프로필 이미지
        const profileImg = document.createElement("img");
        profileImg.classList.add("chatUserProfile");

        if(comment.profileImg == null)  
          profileImg.src = userDefaultIamge; // 기본 이미지
        else                            
          profileImg.src = comment.profileImg; // 회원 이미지

        // 닉네임
        const nickname = document.createElement("h3");
        nickname.classList.add("chatUserName");
        nickname.innerText = comment.memberNickname;
        
        // 작성자 영역(commentWriter)에 프로필, 닉네임, 날짜 추가
        chatContainer.append(profileImg, nickname);
        
        // ----------------------------------------------------

        if(loginMemberNo != comment.memberNo){
        } else if(loginMemberNo == comment.memberNo){
        }
        // 버튼 영역
        const commentBtnArea = document.createElement("div");

        // 답글 버튼
        const childCommentBtn = document.createElement("a");
        childCommentBtn.setAttribute("value", comment.memberNickname);
        childCommentBtn.classList.add("a-insertBtn");
        childCommentBtn.innerText = "답글";

        // 답글 버튼에 onclick 이벤트 리스너 추가 
        childCommentBtn.setAttribute("onclick", 
          `showInsertComment(${comment.commentNo}, this.getAttribute('value'), this)`);     
          
        // 버튼 영역에 답글 추가
        commentBtnArea.append(childCommentBtn);

        // 로그인한 회원 번호가 댓글 작성자 번호와 같을 때
        // 댓글 수정/삭제 버튼 출력

        if(loginMemberNo != null && loginMemberNo == comment.memberNo){

          // 수정 버튼
          const updateBtn = document.createElement("a");
          updateBtn.classList.add("a-updateBtn")
          updateBtn.innerText = "수정";
          updateBtn.style.marginLeft = "3px";
          
          // 수정 버튼에 onclick 이벤트 리스너 추가 
          updateBtn.setAttribute("value", comment.memberEmail);
          updateBtn.setAttribute("name", comment.profileImg);
          updateBtn.setAttribute("onclick", 
          `showUpdateComment(${comment.commentNo}, this.getAttribute('value'), this.getAttribute('name'), this)`); 
          
          // 삭제 버튼
          const deleteBtn = document.createElement("a");
          deleteBtn.classList.add("a-deleteBtn")
          deleteBtn.innerText = "삭제";
          deleteBtn.style.marginLeft = "3px";

          // 삭제 버튼에 onclick 이벤트 리스너 추가 
          deleteBtn.setAttribute("onclick", 
            `deleteComment(${comment.commentNo})`); 


          // 버튼 영역에 수정, 삭제 버튼 추가
          commentBtnArea.append(updateBtn, deleteBtn);
        }

        chatContainer.append(commentBtnArea);

        // 행에 버튼 영역 추가
        // chatContainerUser.append(commentBtnArea);

        // ----------------------------------------------------

        // 날짜(작성일)
        const commentDate = document.createElement("span");
        commentDate.classList.add("chatDate");
        commentDate.innerText = comment.commentWriteDate;

        // 댓글 내용 
        let chatContent = document.createElement("p");
        if(loginMemberNo != comment.memberNo){
          chatContent.classList.add("chatContent");
          chatContent.innerText = comment.commentContent;

          chatContentContainer.append(chatContent); // 행에 내용 추가
          chatContentContainer.append(commentDate); // 행에 작성일자 추가
        } else if(loginMemberNo == comment.memberNo){
          chatContent.classList.remove("chatContent");
          chatContent.classList.add("chatContentUser");
          chatContent.innerText = comment.commentContent;
          
          chatContentContainer.append(commentDate); // 행에 작성일자 추가
          chatContentContainer.append(chatContent); // 행에 내용 추가
        }
      } // else 끝

      // 만약 삭제된 댓글이지만 자식 댓글이 존재하는 경우
      if(comment.commentDelFl == 'Y') {
        // 날짜(작성일)
        const commentDate = document.createElement("span");
        commentDate.classList.add("chatDate");
        commentDate.innerText = comment.commentWriteDate;

        // 댓글 내용 
        let chatContent = document.createElement("p");
        if(loginMemberNo != comment.memberNo){
          chatContent.classList.add("chatContent");
          chatContent.innerText = "삭제된 댓글 입니다";
          
          chatContentContainer.append(chatContent); // 행에 내용 추가
          chatContentContainer.append(commentDate); // 행에 작성일자 추가
        } else if(loginMemberNo == comment.memberNo){
          chatContent.classList.remove("chatContent");
          chatContent.classList.add("chatContentUser");
          chatContent.innerText = "삭제된 댓글 입니다";
          
          chatContentContainer.append(commentDate); // 행에 작성일자 추가
          chatContentContainer.append(chatContent); // 행에 내용 추가
        }
        
      }

      // 댓글 목록(ul)에 행(li) 추가
      chatMainContainer.append(chatContainer);
      chatMainContainer.append(chatContentContainer);
      
      boardCommentDivTop.append(chatMainContainer);

    } // for 끝

  });

}

// -----------------------------------------------------------------------

/* ***** 댓글 등록(ajax) ***** */

const addContent = document.querySelector("#addComment"); // button
const commentContent = document.querySelector("#commentContent"); // textarea

// 댓글 등록 버튼 클릭 시
addContent.addEventListener("click", e => {

  // 로그인이 되어있지 않은 경우
  if(loginMemberNo == null){
    alert("로그인 후 이용해 주세요");
    return; // early return;
  }

  // 댓글 내용이 작성되지 않은 경우
  if(commentContent.value.trim().length == 0){
    alert("내용 작성 후 등록 버튼을 클릭해 주세요");
    commentContent.focus();
    return;
  }

  
  // ajax를 이용해 댓글 등록 요청
  const data = {
    "commentContent" : commentContent.value,
    "boardNo"        : boardNo,
    "memberNo"       : loginMemberNo  // 또는 Session 회원 번호 이용도 가능
  };
  console.log(data);

  fetch("/comment", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(data) // data 객체를 JSON 문자열로 변환
  })

  .then(response => response.text())
  .then(result => {

    if(result > 0){
      alert("댓글이 등록 되었습니다");
      commentContent.value = ""; // 작성한 댓글 내용 지우기
      selectCommentList(); // 댓글 목록을 다시 조회해서 화면에 출력
   
    } else{
      alert("댓글 등록 실패");
    }

  })
  .catch(err => console.log(err));
})

const newinsertComment = (parentCommentNo, memberNickname, btn) => {
  // 댓글 등록 전체 컨테이너
  const chatSubmitMainContainer2 = document.createElement("div");
  chatSubmitMainContainer2.classList.add("chatSubmitMainContainer2");
  chatSubmitMainContainer2.style.display = "flex";
  chatSubmitMainContainer2.style.width = "335px";
  chatSubmitMainContainer2.style.height = "100px";
  chatSubmitMainContainer2.style.background = "var(--primary3)";
  chatSubmitMainContainer2.style.boxShadow = "0px 4px 4px rgba(0, 0, 0, 0.25)";
  chatSubmitMainContainer2.style.borderRadius = "30px";
  chatSubmitMainContainer2.style.overflow = "hidden";
  chatSubmitMainContainer2.style.alignItems = "center";
  chatSubmitMainContainer2.style.marginTop = "10px";

  // 작성할 댓글 내용 감싸는 컨테이너
  const chatSubmitContainer2 = document.createElement("div");
  chatSubmitContainer2.classList.add("chatSubmitContainer2");
  chatSubmitContainer2.style.width = "240px";
  chatSubmitContainer2.style.height = "70px";
  chatSubmitContainer2.style.background = "white";
  chatSubmitContainer2.style.borderRadius = "30px";
  chatSubmitContainer2.style.display = "flex";
  chatSubmitContainer2.style.justifyContent = "center";
  chatSubmitContainer2.style.alignItems = "center";
  chatSubmitContainer2.style.marginLeft = "10px";

  // 답글을 작성할 textarea 요소 생성
  const textarea = document.createElement("textarea");
  textarea.classList.add("textarea");
  textarea.style.width = "250px";
  textarea.style.height = "60px";
  textarea.style.border = "none";
  textarea.style.fontSize = "15px";
  textarea.style.resize = "none";
  textarea.style.borderRadius = "30px";
  textarea.style.padding = "15px";
  textarea.style.letterApacing = "1px";
  textarea.style.lineHeight = "1.5";
  textarea.style.fontFamily = "main";
  textarea.style.outline = "none";
  textarea.placeholder = "답글 등록";

  const div = document.createElement("div");
  div.style.width = "50px";
  div.style.height = "20px";
  div.style.borderColor = "red";

  chatSubmitContainer2.append(textarea);
  
  // 등록 버튼
  const detailCommentSubmitBtn2 = document.createElement("button");
  detailCommentSubmitBtn2.innerText = "등록"; 
  detailCommentSubmitBtn2.setAttribute("onclick", `insertChildComment(${parentCommentNo}, '${memberNickname}', ${btn})`);
  
  detailCommentSubmitBtn2.style.width = "60px";
  detailCommentSubmitBtn2.style.height = "30px";
  detailCommentSubmitBtn2.style.backgroundColor = "var(--primary2)";
  detailCommentSubmitBtn2.style.fontSize = "15px";
  detailCommentSubmitBtn2.style.marginLeft = "10px";
  detailCommentSubmitBtn2.style.transition = "background-color 0.3s";
  detailCommentSubmitBtn2.style.borderColor = "transparent";
  detailCommentSubmitBtn2.style.cursor = "pointer";
  detailCommentSubmitBtn2.style.marginLeft = "10px";
  
  detailCommentSubmitBtn2.addEventListener("mouseover", () => {
    detailCommentSubmitBtn2.style.background = "var(--secondary)";
    detailCommentSubmitBtn2.style.transition = "background-color 0.3s";
    detailCommentSubmitBtn2.style.borderColor= "transparent";
  });
  detailCommentSubmitBtn2.addEventListener("mouseout", () => {
    detailCommentSubmitBtn2.style.width = "60px";
    detailCommentSubmitBtn2.style.height = "30px";
    detailCommentSubmitBtn2.style.backgroundColor = "var(--primary2)";
    detailCommentSubmitBtn2.style.fontSize = "15px";
    detailCommentSubmitBtn2.style.marginLeft = "10px";
    detailCommentSubmitBtn2.style.transition = "background-color 0.3s";
    detailCommentSubmitBtn2.style.borderColor = "transparent";
    detailCommentSubmitBtn2.style.cursor = "pointer";
    detailCommentSubmitBtn2.style.marginLeft = "10px";
  });

  // 취소 버튼
  const detailCommentSubmitBtn3 = document.createElement("button");
  detailCommentSubmitBtn3.innerText = "취소";
  detailCommentSubmitBtn3.setAttribute("onclick", "insertCancel("+btn+")");
  
  detailCommentSubmitBtn3.style.width = "60px";
  detailCommentSubmitBtn3.style.height = "30px";
  detailCommentSubmitBtn3.style.backgroundColor = "var(--primary2)";
  detailCommentSubmitBtn3.style.fontSize = "15px";
  detailCommentSubmitBtn3.style.marginLeft = "10px";
  detailCommentSubmitBtn3.style.transition = "background-color 0.3s";
  detailCommentSubmitBtn3.style.borderColor = "transparent";
  detailCommentSubmitBtn3.style.cursor = "pointer";
  detailCommentSubmitBtn3.style.marginLeft = "10px";
  detailCommentSubmitBtn3.style.marginTop = "5px";
  
  detailCommentSubmitBtn3.addEventListener("mouseover", () => {
    detailCommentSubmitBtn3.style.background = "var(--secondary)";
    detailCommentSubmitBtn3.style.transition = "background-color 0.3s";
    detailCommentSubmitBtn3.style.borderColor= "transparent";
  });
  detailCommentSubmitBtn3.addEventListener("mouseout", () => {
    detailCommentSubmitBtn3.style.width = "60px";
    detailCommentSubmitBtn3.style.height = "30px";
    detailCommentSubmitBtn3.style.backgroundColor = "var(--primary2)";
    detailCommentSubmitBtn3.style.fontSize = "15px";
    detailCommentSubmitBtn3.style.marginLeft = "10px";
    detailCommentSubmitBtn3.style.transition = "background-color 0.3s";
    detailCommentSubmitBtn3.style.borderColor = "transparent";
    detailCommentSubmitBtn3.style.cursor = "pointer";
    detailCommentSubmitBtn3.style.marginLeft = "10px";
    detailCommentSubmitBtn3.style.marginTop = "5px";
  });

  chatSubmitMainContainer2.append(chatSubmitContainer2);
  
  const divBtns = document.createElement("div");
  divBtns.style.display = "flex";
  divBtns.style.flexDirection = "column";
  
  divBtns.append(detailCommentSubmitBtn2);
  divBtns.append(detailCommentSubmitBtn3);
  
  chatSubmitMainContainer2.append(divBtns);

  return chatSubmitMainContainer2; 
  
}

/** 답글 작성 화면 추가
 * @param {*} parentCommentNo 
 * @param {*} btn 
 * @param {*} memberNickname 
 */
const showInsertComment = (parentCommentNo, memberNickname, btn) => {

  // ** 답글 작성 textarea가 한 개만 열릴 수 있도록 만들기 **
  const temp = document.getElementsByClassName("chatSubmitMainContainer2");

  if(temp.length > 0){ // 답글 작성 textara가 이미 화면에 존재하는 경우

    if(confirm("다른 답글을 작성 중입니다. 현재 댓글에 답글을 작성 하시겠습니까?")){
      temp[0].childNodes[0].childNodes[0].remove(); // 버튼 영역부터 삭제
      temp[0].childNodes[0].nextElementSibling.remove(); // 버튼 영역부터 삭제
      temp[0].childNodes[0].remove(); // 버튼 영역부터 삭제
      temp[0].remove(); // 버튼 영역부터 삭제
    
    } else{
      return; // 함수를 종료시켜 답글이 생성되지 않게함.
    }
  }
  const chatSubmitMainContainer2 = newinsertComment(parentCommentNo, memberNickname, btn);

  // 답글 버튼의 부모의 뒤쪽에 textarea 추가
  // after(요소) : 뒤쪽에 추가
  btn.parentElement.parentElement.nextElementSibling.after(chatSubmitMainContainer2);

  // 답글 버튼 영역 + 등록/취소 버튼 생성 및 추가
  // const commentBtnArea = document.createElement("div");
  // commentBtnArea.classList.add("comment-btn-area");

  // const insertBtn = document.createElement("button");
  // insertBtn.innerText = "등록";
  // insertBtn.setAttribute("onclick", "insertChildComment("+parentCommentNo+", this)");

  // const cancelBtn = document.createElement("button");
  // cancelBtn.innerText = "취소";
  // cancelBtn.setAttribute("onclick", "insertCancel(this)");


  // // 답글 버튼 영역의 자식으로 등록/취소 버튼 추가
  // commentBtnArea.append(insertBtn, cancelBtn);

  // // 답글 버튼 영역을 화면에 추가된 textarea 뒤쪽에 추가
  // textarea.after(commentBtnArea);
} 



// ---------------------------------------

/** 답글 (자식 댓글) 작성 취소 
 * @param {*} cancelBtn : 취소 버튼
 */
const insertCancel = (cancelBtn) => {

  // 취소 버튼 부모의 이전 요소(textarea) 삭제
  const commentcancel = document.querySelector(".chatSubmitMainContainer2");
  commentcancel.remove();

  // // 취소 버튼이 존재하는 버튼영역 삭제
  // cancelBtn.parentElement.remove();
}


/** 답글 (자식 댓글) 등록
 * @param {*} parentCommentNo : 부모 댓글 번호
 * @param {*} btn  :  클릭된 등록 버튼
 */
const insertChildComment = (parentCommentNo, memberNickname, btn) => {
  // 답글 내용이 작성된 textarea
  console.log(memberNickname);
  const textarea = document.querySelector(".chatSubmitContainer2").childNodes[0];
  // const idx = memberNickname.indexOf("@");
  // const memberEmail = memberNickname.substr(0, idx);

  // 유효성 검사
  if(textarea.value.trim().length == 0){
    alert("내용 작성 후 등록 버튼을 클릭해 주세요");
    textarea.focus();
    return;
  }

  // ajax를 이용해 댓글 등록 요청
  const data = {
    "commentContent" : "@" + memberNickname + " " + textarea.value,
    "boardNo"        : boardNo,
    "memberNo"       : loginMemberNo,  // 또는 Session 회원 번호 이용도 가능
    "parentCommentNo" : parentCommentNo // 부모 댓글 번호
  };

  fetch("/comment", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(data) // data 객체를 JSON 문자열로 변환
  })

  .then(response => response.text())
  .then(result => {

    if(result > 0){
      alert("답글이 등록 되었습니다");
      selectCommentList(); // 댓글 목록을 다시 조회해서 화면에 출력
  
    } else{
      alert("답글 등록 실패");
    }

  })
  .catch(err => console.log(err));



}


// --------------------------------------------------

/** 댓글 삭제
 * @param {*} commentNo 
 */
const deleteComment = commentNo => {

  // 취소 선택 시
  if(!confirm("삭제 하시겠습니까?")) return;

  fetch("/comment",{
    method : "DELETE",
    headers : {"Content-Type" : "application/json"},
    body : commentNo
  })
  .then( resp => resp.text() )
  .then( result => {

    if(result > 0){
      alert("삭제 되었습니다");
      selectCommentList(); // 다시 조회해서 화면 다시 만들기
    
    } else {
      alert("삭제 실패");
    }

  })
  .catch( err => console.log(err));

}


// ----------------------------------

// 수정 취소 시 원래 댓글 형태로 돌아가기 위한 백업 변수
let beforeCommentRow;

/** 댓글 수정 화면 전환
 * @param {*} commentNo 
 * @param {*} btn 
 */
const showUpdateComment = (commentNo, memberNickname, img, btn) => {

  /* 댓글 수정 화면이 1개만 열릴 수 있게 하기 */
  const temp = document.querySelector(".update-textarea");

  // .update-textarea 존재 == 열려있는 댓글 수정창이 존재
  if(temp != null){

    if(confirm("수정 중인 댓글이 있습니다. 현재 댓글을 수정 하시겠습니까?")){
      const commentRow = temp.parentElement.parentElement; // 기존 댓글 행
      commentRow.after(beforeCommentRow); // 기존 댓글 다음에 백업 추가
      commentRow.remove(); // 기존 삭제 -> 백업이 기존 행 위치로 이동

    } else{ // 취소
      return;
    }
  }

  // -------------------------------------------
  
  // 1. 댓글 수정이 클릭된 행 (.comment-row) 선택
  const chatMainContainertemp = btn.parentElement.parentElement.parentElement;
  
  // 2. 행 전체를 백업(복제)
  // 요소.cloneNode(true) : 요소 복제, 
  //           매개변수 true == 하위 요소도 복제
  beforeCommentRow = chatMainContainertemp.cloneNode(true);
  // console.log(beforeCommentRow);

  // 3. 기존 댓글에 작성되어 있던 내용만 얻어오기
  let beforeContent = chatMainContainertemp.children[1].children[1].innerText;

  // 4. 댓글 행 내부를 모두 삭제
  // chatMainContainertemp.innerHTML = "";
  chatMainContainertemp.children[1].innerHTML = "";
  chatMainContainertemp.children[0].children[2].remove(); 

  const commentDate = document.createElement("span");
  commentDate.classList.add("chatDate");
  commentDate.innerText = "수정 중";
  commentDate.style.marginLeft = "74px";

  const textarea = document.createElement("textarea");
  textarea.classList.add("update-textarea");
  textarea.classList.add("chatContentUser");
  textarea.textContent = beforeContent;
  textarea.style.resize = "none";
  textarea.style.outline = "none";
  textarea.style.height = "52px";

  const chatContentUser = document.querySelector("p");
  chatContentUser.classList.add("chatContentUser");

  chatContentUser.append(textarea);
  
  chatMainContainertemp.children[1].append(commentDate, textarea);

  // 버튼 영역
  const commentBtnArea = document.createElement("div");
  commentBtnArea.classList.add("comment-btn-area");
  commentBtnArea.style.width = "auto";
  commentBtnArea.style.marginLeft = "34px";

  // 수정 버튼
  const updateBtn = document.createElement("a");
  updateBtn.classList.add("a-updateBtn")
  updateBtn.innerText = "수정";
          
  // 수정 버튼에 onclick 이벤트 리스너 추가 
  updateBtn.setAttribute("onclick", `updateComment(${commentNo}, this)`);
          
  // 취소 버튼
  const cancelBtn = document.createElement("a");
  cancelBtn.classList.add("a-deleteBtn")
  cancelBtn.innerText = "취소";
  cancelBtn.style.marginLeft = "8px";

  // 취소 버튼에 onclick 이벤트 리스너 추가 
  cancelBtn.setAttribute("onclick", `updateCancel(this)`);

  // 버튼 영역에 수정, 삭제 버튼 추가
  commentBtnArea.append(updateBtn, cancelBtn);
  chatMainContainertemp.children[0].append(commentBtnArea);

}
// --------------------------------------------------------------------

/** 댓글 수정 취소
 * @param {*} btn : 취소 버튼
 */
const updateCancel = (btn) => {
  if(confirm("취소 하시겠습니까?")){
    const commentRow = btn.parentElement.parentElement.parentElement;  // 기존 댓글 행
    commentRow.after(beforeCommentRow); // 기존 댓글 다음에 백업 추가
    commentRow.remove(); // 기존 삭제 -> 백업이 기존 행 위치로 이동
  }

}


// ----------------------------------------------------------

/** 댓글 수정
 * @param {*} commentNo : 수정할 댓글 번호
 * @param {*} btn       : 클릭된 수정 버튼
 */
const updateComment = (commentNo, btn) => {
  // 수정된 내용이 작성된 textarea 얻어오기
  const textarea = btn.parentElement.parentElement.parentElement.children[1].children[1];
  console.log(textarea.value);
  // 유효성 검사
  if(textarea.value.trim().length == 0){
    alert("댓글 작성 후 수정 버튼을 클릭해 주세요");
    textarea.focus();
    return;
  }

  // 댓글 수정 (ajax)
  const data = {
    "commentNo" : commentNo,
    "commentContent" : textarea.value
  }

  fetch("/comment", {
    method : "PUT",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(data)
  })
  .then(resp => resp.text())
  .then(result => {
    if(result > 0){
      alert("댓글이 수정 되었습니다");
      selectCommentList();
    } else {
      alert("댓글 수정 실패");
    }

  })
  .catch(err => console.log(err));
}