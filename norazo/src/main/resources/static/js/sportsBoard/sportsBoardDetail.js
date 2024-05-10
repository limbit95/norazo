// 모임글 상세조회

// 메인 참여인원 아이콘
const joinMemberStatus = document.querySelector(".joinMemberStatus");




// Board Content
// 모임 소개
const boardContent = document.querySelector("#boardContent");

if (boardContent && boardContent.innerHTML.trim() === "") {
    boardContent.innerHTML = "존재하지 않는 소개글 입니다";
    boardContent.style.color = "#3C3C3C";
}



//  Attended Member
// 참여 인원
function updateMemberInfo() {
    // Get the element by its ID
    const memberInfo = document.getElementById('memberInfo');
  
    // Read data attributes for attend and limit
    const attendMemberCount = parseInt(memberInfo.getAttribute('data-attend'), 10);
    const memberCountLimit = parseInt(memberInfo.getAttribute('data-limit'), 10);
  
    // Calculate the remaining spots
    const remaining = memberCountLimit - attendMemberCount;
  
    // Update the text content of the element
    memberInfo.textContent = `${attendMemberCount} / ${memberCountLimit} (${remaining} 자리 남음)`;
  }
  
  // Ensure the DOM is fully loaded before running the script
  document.addEventListener('DOMContentLoaded', updateMemberInfo);


// Member Info
// 모임장 자기소개
const memberIntroduce = document.querySelector("#memberIntroduce");
  
if (memberIntroduce && memberIntroduce.innerHTML.trim() === "") {
    memberIntroduce.innerHTML = "자기소개 비어있어요~";
    memberIntroduce.style.color = "#E1E1E1";
}
// console.log("Sports Board Detail");
  

// 모임글 취소
const deleteJoinMember = document.querySelector("#deleteJoinMember");

if (deleteJoinMember != null) {

    
    deleteJoinMember.addEventListener("click", (e) => {
        const memberNo = document.querySelector("#memberNo").value;
        const createMemberNo = document.querySelector("#createMemberNo").value;
        const loginMemberNo = document.querySelector("#loginMemberNo").value;
        if (memberNo == loginMemberNo) {

            if(confirm("모임 삭제")) {
                alert("모임이 삭제 되었습니다");
            } else {
                e.preventDefault();
            }
        } 

        if (memberNo != loginMemberNo || createMemberNo != loginMemberNo) {
            if(confirm("참여 취소")) {
                alert("참여 취소 되었습니다");
            } else {
                e.preventDefault();
            }
        }

    });
}





// if (createMemberNo == memberNo) {
//     deleteJoinMember.innerHTML = "모임 삭제";
// } else {
//     deleteJoinMember.innerHTMK = "모임 취소";
// }




// 게시글 수정 삭제 테스트 코드
const updateBtn = document.querySelector("#updateSportsBoard");
const deleteBtn = document.querySelector("#deleteSportsBoard");

if(updateBtn != null){
    updateBtn.addEventListener("click", e =>{
        const boardNo = e.target.dataset.boardNo;
        const sportsCode = e.target.dataset.sportsCode;
        location.href = "/editBoard/update/" + sportsCode + "/" + boardNo;
    });
};

if(deleteBtn != null){
    deleteBtn.addEventListener("click", e => {
        const boardNo = e.target.dataset.boardNo;
        const sportsCode = e.target.dataset.sportsCode;
        if(!confirm("정말로 삭제하시겠습니까?")){
            return;
        }
        
        location.href = "/sportsBoard/delete/" + sportsCode + "/" + boardNo;
    });  
};