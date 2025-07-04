console.log("Script loaded....");

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
    changeTheme();
});

// TODO 
function changeTheme() {
    document.querySelector("html").classList.add(currentTheme);

    const changeThemeButton = document.querySelector("theme-change-button");
    
    changeThemeButton.querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";
     
    changeThemeButton.addEventListener("click",(event) => {
        const oldTheme = currentTheme;
        console.log("change theme button clicked");
        if(currentTheme == "dark") {
            currentTheme = "light";
        } else {
            currentTheme = "dark";
        }
    });

    setTheme(currentTheme);

    document.querySelector("html").classList.remove(oldTheme);
    
    document.querySelector("html").classList.add(currentTheme);

}

// TODO set theme


// TODO set theme to local storage
function setTheme(theme) {
    
}


