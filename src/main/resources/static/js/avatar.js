console.log("avatar.js loaded");

// Elements
const fileInput = document.getElementById("file_input");
const avatar = document.getElementById("avatarPreview");

const maleRadio = document.getElementById("default-radio-1");
const femaleRadio = document.getElementById("default-radio-2");

// Safety check (VERY IMPORTANT)
if (fileInput && avatar && maleRadio && femaleRadio) {

    const DEFAULT_AVATAR = "/images/avatar-default.png";
    const MALE_AVATAR = "/images/avatar-male.png";
    const FEMALE_AVATAR = "/images/avatar-female.png";

    const MIN_SIZE = 50 * 1024;
    const MAX_SIZE = 1024 * 1024;

    function applyDefaultAvatar() {
        if (fileInput.files && fileInput.files.length > 0) {
            const file = fileInput.files[0];
            if (
                file.type.startsWith("image/") &&
                file.size >= MIN_SIZE &&
                file.size <= MAX_SIZE
            ) {
                return;
            }
        }

        if (femaleRadio.checked) {
            avatar.src = FEMALE_AVATAR;
        } else if (maleRadio.checked) {
            avatar.src = MALE_AVATAR;
        } else {
            avatar.src = DEFAULT_AVATAR;
        }
    }

    fileInput.addEventListener("change", () => {
        if (!fileInput.files.length) {
            applyDefaultAvatar();
            return;
        }

        const file = fileInput.files[0];
        if (
            !file.type.startsWith("image/") ||
            file.size < MIN_SIZE ||
            file.size > MAX_SIZE
        ) {
            applyDefaultAvatar();
            return;
        }

        const reader = new FileReader();
        reader.onload = e => avatar.src = e.target.result;
        reader.readAsDataURL(file);
    });

    maleRadio.addEventListener("change", applyDefaultAvatar);
    femaleRadio.addEventListener("change", applyDefaultAvatar);

} else {
    console.warn("Avatar elements not found in DOM");
}
