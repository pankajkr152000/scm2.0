(function () {

    document.addEventListener("DOMContentLoaded", () => {

        // ================= ELEMENTS =================
        const form = document.querySelector("form");
        const submitBtn = document.getElementById("submitBtn");
        const serverErrors = document.getElementById("serverErrors");

        const fields = {
            fullName: document.getElementById("fullName"),
            contactNumber: document.getElementById("contactNumber"),
            email: document.getElementById("email"),
            dob: document.getElementById("datepicker-actions"),
            website: document.querySelector("input[id='contactWebsite']"),
            linkedin: document.querySelector("input[id='contactLinkedIn']"),
            image: document.getElementById("file_input")
        };

        const fileError = document.getElementById("fileError");

        // ================= UI HELPERS =================
        function showError(input, message) {
            clearError(input);
            const p = document.createElement("p");
            p.className = "text-red-600 text-xs mt-1";
            p.innerText = message;
            input.closest("div").appendChild(p);
            input.classList.add("border-red-500");
            input.classList.remove("border-green-500");
        }

        function showSuccess(input) {
            clearError(input);
            input.classList.add("border-green-500");
        }

        function clearError(input) {
            input.classList.remove("border-red-500", "border-green-500");
            const parent = input.closest("div");
            if (!parent) return;
            parent.querySelectorAll("p.text-red-600").forEach(e => e.remove());
        }

        // ================= VALIDATORS =================
        const regex = {
            mobile: /^[6-9]\d{9}$/,
            email: /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        };

        function isValidUrl(url) {
            try { new URL(url); return true; } catch { return false; }
        }

        function validateDOB(value) {
            if (!value) return true;
            const [dd, mm, yyyy] = value.split("-");
            return new Date(`${yyyy}-${mm}-${dd}`) <= new Date();
        }

        function validateImage(file) {
            if (!file) return true;
            if (!["image/jpeg", "image/png", "image/webp"].includes(file.type))
                return "Invalid image type";
            if (file.size > 2 * 1024 * 1024)
                return "Image must be ≤ 2MB";
            return true;
        }

        // ================= FIELD RULES =================
        const rules = {
            fullName: () => {
                const v = fields.fullName.value.trim();
                if (!v) return "Contact name is required";
                if (v.length < 3) return "Minimum 3 characters required";
                return true;
            },
            contactNumber: () => {
                const v = fields.contactNumber.value.trim();
                if (!v) return "Contact number is required";
                if (!regex.mobile.test(v)) return "Invalid Indian mobile number";
                return true;
            },
            email: () => {
                const v = fields.email.value.trim();
                if (!v) return true;
                if (!regex.email.test(v)) return "Invalid email address";
                return true;
            },
            dob: () => validateDOB(fields.dob.value) || "DOB cannot be in future",
            website: () => !fields.website.value || isValidUrl(fields.website.value) || "Invalid website URL",
            linkedin: () => !fields.linkedin.value || isValidUrl(fields.linkedin.value) || "Invalid LinkedIn URL"
        };

        // ================= VALIDATION ENGINE =================
        function validateField(key) {
            const input = fields[key];
            const result = rules[key]();
            if (result === true) {
                showSuccess(input);
                return true;
            }
            showError(input, result);
            return false;
        }

        function validateForm() {
            let valid = true;
            Object.keys(rules).forEach(key => {
                if (!validateField(key)) valid = false;
            });

            if (fields.image.files.length) {
                const imgResult = validateImage(fields.image.files[0]);
                if (imgResult !== true) {
                    fileError.innerText = imgResult;
                    fileError.classList.remove("hidden");
                    valid = false;
                }
            }

            submitBtn.disabled = !valid;
            submitBtn.classList.toggle("opacity-50", !valid);
            submitBtn.classList.toggle("cursor-not-allowed", !valid);

            return valid;
        }

        // ================= REAL-TIME EVENTS =================
        Object.keys(rules).forEach(key => {
            fields[key].addEventListener("blur", () => {
                validateField(key);
                validateForm();
            });
        });

        fields.contactNumber.addEventListener("input", () => {
            fields.contactNumber.value =
                fields.contactNumber.value.replace(/\D/g, "").slice(0, 10);
        });

        fields.image.addEventListener("change", () => validateForm());

        // ================= SUBMIT =================
        form.addEventListener("submit", (e) => {
            if (!validateForm()) {
                e.preventDefault();
            }
        });

        // ================= SPRING ERROR SYNC =================
        if (serverErrors && serverErrors.innerText.trim()) {
            serverErrors.classList.remove("hidden");
        }

        // Initial state
        validateForm();
    });

})();
