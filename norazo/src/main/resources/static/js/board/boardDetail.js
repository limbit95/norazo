const updateBoardBtn = document.querySelector(".updateBoardBtn");

if(updateBoardBtn != null){

    updateBoardBtn.addEventListener("click", () => {
        // 현재 주소 : http://localhost/board/free/1189?cp=1
        // 목표 주소 : http://localhost/board/free/update/1189?cp=1

        location.href = location.pathname.replace("/board/free/", "/board/free/update/")
                        + location.search;
                        
    });
}
