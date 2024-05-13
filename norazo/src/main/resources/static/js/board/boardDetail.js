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

// 페이지 네이션 이동 

const listButton = document.querySelector("#listButton");

listButton.addEventListener("click", () => {
  // 현재 URL의 경로를 가져옵니다.
  let url = location.pathname;
  
  url = url.substring(0,url.lastIndexOf("/"));
  
  // 수정된 URL로 페이지를 리다이렉트합니다.
  location.href = url + location.search;
});

const leftPageButton = document.querySelector("#leftPageButton");

// leftPageButton.addEventListener("click",() =>{

  
// });
// 현재 주소 
// http://localhost/board/faq/100?cp=1
// 목표 주소 
// http://localhost/board/faq/101?cp=1
