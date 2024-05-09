// 모임글 상세조회

// 메인 참여인원 아이콘
const joinMemberStatus = document.querySelector(".joinMemberStatus]");




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
const createMemberNo = document.querySelector("#deleteJoinMember");

if (createMemberNo != null) {

    
    createMemberNo.addEventListener("click", () => {
        

        if(confirm("모임에 취소 하시겠습니까?")) {
            alert("모임 취소 되었습니다");
        } else {
            alert("모임에 취소 되지 않았습니다");
        }

    });
}