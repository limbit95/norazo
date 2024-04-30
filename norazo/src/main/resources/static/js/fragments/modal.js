const modalContent = document.querySelector(".modal-content");
const thumbnail = document.querySelector(".thumbnail");
const closeBtn = document.querySelector(".close-btn"); 
const boardTitle = document.querySelector(".board-title"); 

thumbnail.addEventListener("click", () => {
    modalContent.classList.toggle("popup-hidden");
});

boardTitle.addEventListener("click", () => {
    modalContent.classList.toggle("popup-hidden");
});

closeBtn.addEventListener("click", () => {
    modalContent.classList.toggle("popup-hidden");
});