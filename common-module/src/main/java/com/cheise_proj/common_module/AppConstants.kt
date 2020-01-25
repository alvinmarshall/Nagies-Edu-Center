package com.cheise_proj.common_module

val LOGIN_ROLE_OPTIONS = arrayListOf("parent", "teacher")
const val USER_SELECTED_ROLE_PREF_KEY = "USER_SELECTED_ROLE_PREF_KEY"
const val INFORDAS_BASE_URL = "https://infodasgh.com/api/v1/"
const val DEV_INFORDAS_BASE_URL = "https://developers.nagies.infodasgh.com/api/v1/"
const val DATABASE_NAME = "app_db"
const val USER_LOGIN_STATUS_PREF_KEY = "login_in_status"
const val USER_TOKEN_PREF_KEY = "user_token"
const val SERVER_URL = "https://infodasgh.com/api/v1/file/download?path="
const val DEV_SERVER_URL = "https://developers.nagies.infodasgh.com/api/v1/file/download?path="
const val PERMISSION_PREF = "permission_pref"
const val REQUEST_EXTERNAL_STORAGE = 100
const val USER_FULL_NAME_PREF_KEY = "full_name_key"
const val USER_LEVEL_NAME_PREF_KEY = "level_name_key"
const val USER_IMAGE_URL_PREF_KEY = "image_url_key"
const val USER_NAME_PREF_KEY = "username_key"
enum class DBEntities {
    ASSIGNMENT, REPORT, CIRCULAR, BILLING, TIME_TABLE
}

enum class MessageType {
    MESSAGES, FILES, TEACHERS
}

enum class ClassTeacherAction {
    CALL, MESSAGE
}

enum class UserAccount {
    PARENT, TEACHER
}

enum class ComplaintAction {
    DETAILS, CALL, MESSAGE
}

const val FOREGROUND_PUSH_NOTIFICATION_EXTRA = "push_extras"
const val NOTIFICATION_MESSAGE_EXTRAS = "notification_message_extras"
const val NOTIFICATION_EXTRA_MESSAGE = "notification_message_extra"
const val NOTIFICATION_EXTRA_REPORT = "notification_report_extra"
const val NOTIFICATION_EXTRA_ASSIGNMENT = "notification_assignment_extra"
const val NOTIFICATION_EXTRA_COMPLAINT = "notification_complaint_extra"
const val FIREBASE_TOKEN_ID = "firebase_token_id"
const val FOREGROUND_PUSH_NOTIFICATION = "pushNotification"

const val NAVIGATE_TO_DASHBOARD = "Messages"
const val NAVIGATE_TO_ASSIGNMENT_IMAGE = "Assignment-Image"
const val NAVIGATE_TO_ASSIGNMENT_PDF = "Assignment-PDF"
const val NAVIGATION_TO_COMPLAINT = "Complaint"
const val NAVIGATE_TO_DIALOG_RESET_PASSWORD = "passwordDialog"
const val NAVIGATE_TO_ANNOUNCEMENT = "Announcement"
const val NAVIGATE_TO_REPORT_IMAGE = "Report-Image"
const val NAVIGATE_TO_REPORT_PDF = "Report-PDF"

const val MESSAGE_RECEIVE_EXTRA = "message_extra"
const val COMPLAINT_RECEIVE_EXTRA = "complaint_extra"
const val ANNOUNCEMENT_RECEIVE_EXTRA = "announcement_extra"
const val ASSIGNMENT_IMAGE_RECIEVE_EXTRA = "assignment_image_extra"
const val ASSIGNMENT_PDF_RECIEVE_EXTRA ="assignment_pdf_extra"
const val REPORT_IMAGE_RECEIVE_EXTRA ="report_image_extra"
const val REPORT_PDF_RECEIVE_EXTRA = "report_pdf_extra"

enum class FetchType {
    ASSIGNMENT_PDF, ASSIGNMENT_IMAGE, REPORT_PDF, REPORT_IMAGE, MESSAGE,
    ANNOUNCEMENT, COMPLAINT, CLASS_TEACHER, CIRCULAR, BILLING, CLASS_STUDENT,
    TIME_TABLE
}

const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
const val IMAGE_FORMAT = "image"
const val PDF_FORMAT = "pdf"

enum class CircularAction {
    VIEW, DOWNLOAD
}

enum class ViewFilesAction {
    VIEW, DOWNLOAD, DELETE
}

enum class FileUploadFormat {
    PDF, IMAGE
}

const val FILE_CHOOSER_RESULT = 22

enum class UploadFileType {
    NORMAL, REPORT
}