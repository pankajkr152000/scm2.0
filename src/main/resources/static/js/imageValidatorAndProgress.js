const MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
const ALLOWED_TYPES = [
  "image/jpeg",
  "image/png",
  "image/jpg",
  "image/gif",
  "image/webp",
];

let selectedFile = null;
function handleImageSelect(input) {
    
  // ✅ RESET UI FOR NEW SELECTION
  const uploadBtn = document.getElementById("uploadBtn");
  const progressWrapper = document.getElementById("progressWrapper");
  const progressText = document.getElementById("progressText");
  const progressBar = document.getElementById("progressBar");

  uploadBtn.disabled = false;
  uploadBtn.textContent = "Upload";

  progressWrapper.style.display = "none";
  progressText.style.display = "none";
  progressBar.style.width = "0%";

  selectedFile = null;

  const file = input.files[0];
  const errorEl = document.getElementById("fileError");
  const preview = document.getElementById("avatarPreview");

  errorEl.classList.add("hidden");
  errorEl.textContent = "";

  if (!file) return;

  // Validate type
  if (!ALLOWED_TYPES.includes(file.type)) {
    showError("Only JPG, PNG, GIF, WEBP allowed");
    reset();
    return;
  }

  // Validate size
  if (file.size > MAX_FILE_SIZE) {
    showError("Image size must be under 2MB");
    reset();
    return;
  }

  selectedFile = file;

  // Preview
  const reader = new FileReader();
  reader.onload = (e) => (preview.src = e.target.result);
  reader.readAsDataURL(file);

  function showError(msg) {
    errorEl.textContent = msg;
    errorEl.classList.remove("hidden");
  }

  function reset() {
    input.value = "";
    preview.src = "/images/avatar-default.png";
    selectedFile = null;
  }
}

function uploadImage(event) {
  if (event) event.preventDefault();

  if (!selectedFile) {
    alert("Please select a valid image first");
    return;
  }

  const uploadBtn = document.getElementById("uploadBtn");
  uploadBtn.disabled = true;
  uploadBtn.textContent = "Uploading...";

  const formData = new FormData();
  formData.append("picture", selectedFile);

  const xhr = new XMLHttpRequest();
  xhr.open("POST", "/user/contacts/upload-avatar", true);

  // CSRF
  const csrfTokenEl = document.querySelector('meta[name="_csrf"]');
  const csrfHeaderEl = document.querySelector('meta[name="_csrf_header"]');
  if (csrfTokenEl && csrfHeaderEl) {
    xhr.setRequestHeader(csrfHeaderEl.content, csrfTokenEl.content);
  }

  const progressWrapper = document.getElementById("progressWrapper");
  const progressBar = document.getElementById("progressBar");
  const progressText = document.getElementById("progressText");

  // FORCE show
  progressWrapper.style.display = "block";
  progressText.style.display = "block";

  xhr.upload.onprogress = function (event) {
    if (!event || !event.lengthComputable) return;

    const percent = Math.round((event.loaded / event.total) * 100);
    progressBar.style.width = percent + "%";
    progressText.textContent = percent + "%";
  };

  xhr.onload = function () {
    if (xhr.status === 200) {
      progressText.textContent = "Upload complete ✔";
    } else {
      progressText.textContent = "Upload failed ❌";
    }
  };

  xhr.onerror = function () {
    progressText.textContent = "Upload error ❌";
  };

  xhr.onloadend = function () {
    uploadBtn.disabled = false;
    uploadBtn.textContent = "Image Uploaded";
  };

  xhr.send(formData);
}


// function resetUploadState() {
//   selectedFile = null;

//   const input = document.getElementById("file_input");
//   const progressBar = document.getElementById("progressBar");
//   const progressWrapper = document.getElementById("progressWrapper");
//   const progressText = document.getElementById("progressText");

//   input.value = "";
//   progressBar.style.width = "0%";
//   progressWrapper.style.display = "none";
//   progressText.style.display = "none";
//   progressText.textContent = "0%";
// }
function resetUploadState() {
  selectedFile = null;

  const input = document.getElementById("file_input");
  const progressBar = document.getElementById("progressBar");
  const progressWrapper = document.getElementById("progressWrapper");
  const progressText = document.getElementById("progressText");

  input.value = "";
  progressBar.style.width = "0%";
  progressWrapper.style.display = "none";
  progressText.style.display = "none";
}

