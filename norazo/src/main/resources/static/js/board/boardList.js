const myGroupSelect = document.querySelector("#myGroupSelect");

if(myGroupSelect != null){
    myGroupSelect.addEventListener("change", e =>{
        console.log(e.target.value);
        location.href = "/myPage/" + e.target.value;
    });
}
