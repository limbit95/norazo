// 게시글 수정 이동 
const updateBoardBtn = document.querySelector(".updateBoardBtn");

if(updateBoardBtn != null){


    updateBoardBtn.addEventListener("click", () => {
        // 현재 주소 : http://localhost/board/free/1189?cp=1
        // 목표 주소 : http://localhost/board/free/update/1189?cp=1

        location.href = location.pathname
                        + "/update"            
                        + location.search;
                        
    });
}
// 게시글 삭제 
const deleteBoardBtn = document.querySelector(".deleteBoardBtn");

if(deleteBoardBtn != null){
    deleteBoardBtn.addEventListener("click", () => {
    if( !confirm("삭제 하시겠습니까?") ) {
      alert("취소 되었습니다.")
      return;
    }

    location.href = location.pathname
    + "/delete"            
    + location.search;
  });
}