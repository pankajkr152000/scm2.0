console.log("Script loaded");

let currentTheme = "light";

document.addEventListener("DOMContentLoaded", () => {
  currentTheme = getTheme();
  applyTheme(currentTheme);
  setupThemeToggle();
});

// ONLY handles UI
function applyTheme(theme) {
  const html = document.documentElement;

  html.classList.remove("light", "dark");
  html.classList.add(theme);

  const textSpan = document.querySelector("#theme-change-button span");
  if (textSpan) {
    textSpan.textContent = theme === "light" ? "Dark" : "Light";
  }
}

// ONLY handles click
function setupThemeToggle() {
  const btn = document.querySelector("#theme-change-button");
  if (!btn) return;

  btn.addEventListener("click", () => {
    currentTheme = currentTheme === "dark" ? "light" : "dark";

    setTheme(currentTheme);     // ✅ SAVE ONLY HERE
    applyTheme(currentTheme);  // ✅ APPLY ONLY HERE
  });
}

function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

function getTheme() {
  return localStorage.getItem("theme") || "light";
}
