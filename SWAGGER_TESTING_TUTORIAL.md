# 📚 Complete Swagger API Testing Tutorial

This comprehensive guide will walk you through testing every endpoint in your Spring Boot Authentication System using Swagger UI.

## 🚀 Getting Started

### 1. Start the Application
```bash
mvn spring-boot:run
```

### 2. Access Swagger UI
Open your browser and navigate to: **http://localhost:8080/api/swagger-ui.html**

---

## 🔑 Default Test Accounts

| Username | Password | Role        | Email              |
|----------|----------|-------------|--------------------|
| admin    | admin123 | ADMIN, USER | admin@example.com  |
| user     | user123  | USER        | user@example.com   |

---

## 📋 Testing Sequence

### **Step 1: Test Public Endpoints** 

#### 🌐 Test Public Access
1. Expand **"Test Endpoints"** section
2. Click **GET /test/public**
3. Click **"Try it out"**
4. Click **"Execute"**
5. ✅ **Expected**: 200 response with message "Public Content - Accessible to everyone!"

---

### **Step 2: User Registration**

#### 👤 Register a New User
1. Expand **"Authentication"** section
2. Click **POST /auth/signup**
3. Click **"Try it out"**
4. Replace the request body with:
```json
{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "password123",
  "firstName": "Test",
  "lastName": "User",
  "phoneNumber": "+1234567890"
}
```
5. Click **"Execute"**
6. ✅ **Expected**: 200 response with message "User registered successfully!"

#### ❌ Test Duplicate Registration
1. Try the same registration again
2. ✅ **Expected**: 409 Conflict error - "Username is already taken!" or "Email is already in use!"

---

### **Step 3: User Authentication**

#### 🔐 Login with New User
1. Click **POST /auth/signin**
2. Click **"Try it out"**
3. Use this request body:
```json
{
  "usernameOrEmail": "testuser",
  "password": "password123"
}
```
4. Click **"Execute"**
5. ✅ **Expected**: 200 response with JWT tokens and user info
6. **📝 IMPORTANT**: Copy the `accessToken` from the response - you'll need it!

#### 🔐 Login with Admin Account
1. Use the same endpoint with admin credentials:
```json
{
  "usernameOrEmail": "admin",
  "password": "admin123"
}
```
2. ✅ **Expected**: 200 response with admin tokens
3. **📝 IMPORTANT**: Copy the admin `accessToken` for admin-only tests

#### ❌ Test Invalid Login
1. Try with wrong credentials:
```json
{
  "usernameOrEmail": "testuser",
  "password": "wrongpassword"
}
```
2. ✅ **Expected**: 401 Unauthorized

---

### **Step 4: JWT Token Authentication Setup**

#### 🔑 Configure Bearer Token in Swagger
1. At the top of Swagger UI, click the **"Authorize"** button (🔒)
2. In the popup, enter: `Bearer YOUR_ACCESS_TOKEN`
   - Replace `YOUR_ACCESS_TOKEN` with the token you copied
   - Example: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTY...`
3. Click **"Authorize"**
4. Click **"Close"**
5. ✅ **Confirmation**: You'll see a 🔒 icon next to protected endpoints

---

### **Step 5: Test Protected User Endpoints**

#### 👤 Get Current User Info
1. Click **GET /users/me**
2. Click **"Try it out"**
3. Click **"Execute"**
4. ✅ **Expected**: 200 response with your user details

#### ✏️ Update Current User
1. Click **PUT /users/me**
2. Click **"Try it out"**
3. Use this request body:
```json
{
  "firstName": "Updated",
  "lastName": "Name",
  "phoneNumber": "+9876543210"
}
```
4. Click **"Execute"**
5. ✅ **Expected**: 200 response with updated user info

#### 🔐 Test User Role Access
1. Click **GET /test/user**
2. Click **"Try it out"**
3. Click **"Execute"**
4. ✅ **Expected**: 200 response with "User Content - Accessible to users with USER or ADMIN role!"

---

### **Step 6: Test Admin-Only Endpoints**

#### 🔑 Switch to Admin Token
1. Click **"Authorize"** button again
2. Replace with admin token: `Bearer ADMIN_ACCESS_TOKEN`
3. Click **"Authorize"** then **"Close"**

#### 👥 Get All Users (Admin Only)
1. Click **GET /users**
2. Click **"Try it out"**
3. Click **"Execute"**
4. ✅ **Expected**: 200 response with list of all users

#### 👤 Get User by ID (Admin Only)
1. Click **GET /users/{id}**
2. Click **"Try it out"**
3. Enter `1` in the id field
4. Click **"Execute"**
5. ✅ **Expected**: 200 response with specific user details

#### ✏️ Update Any User (Admin Only)
1. Click **PUT /users/{id}**
2. Click **"Try it out"**
3. Enter `2` in the id field (or any existing user ID)
4. Use request body:
```json
{
  "firstName": "Admin Updated",
  "phoneNumber": "+1111111111"
}
```
5. Click **"Execute"**
6. ✅ **Expected**: 200 response with updated user

#### 🗑️ Delete User (Admin Only)
1. Click **DELETE /users/{id}**
2. Click **"Try it out"**
3. Enter a user ID (not admin's ID)
4. Click **"Execute"**
5. ✅ **Expected**: 200 response with "User deleted successfully!"

#### 🔐 Test Admin Access
1. Click **GET /test/admin**
2. Click **"Try it out"**
3. Click **"Execute"**
4. ✅ **Expected**: 200 response with "Admin Content - Accessible to users with ADMIN role only!"

---

### **Step 7: Test Permission Boundaries**

#### ❌ Test Access Denied (Switch back to regular user token)
1. Authorize with regular user token
2. Try **GET /users** (admin only)
3. ✅ **Expected**: 403 Forbidden

#### ❌ Test Without Token
1. Click **"Authorize"** and click **"Logout"**
2. Try **GET /users/me**
3. ✅ **Expected**: 401 Unauthorized

---

### **Step 8: Test Token Refresh**

#### 🔄 Refresh Access Token
1. Click **POST /auth/refreshtoken**
2. Click **"Try it out"**
3. Use the refresh token from your login response:
```json
{
  "refreshToken": "YOUR_REFRESH_TOKEN_HERE"
}
```
4. Click **"Execute"**
5. ✅ **Expected**: 200 response with new access token

---

### **Step 9: Test Password Recovery**

#### 📧 Request Password Reset
1. Click **POST /auth/forgot-password**
2. Click **"Try it out"**
3. Enter `testuser@example.com` in the email parameter
4. Click **"Execute"**
5. ✅ **Expected**: 200 response with "Password reset email sent!"

#### ❌ Test with Non-existent Email
1. Try with `nonexistent@example.com`
2. ✅ **Expected**: 200 response (same message - prevents user enumeration)

#### 🔑 Reset Password (Simulated)
> **Note**: In development, check the console logs for the reset token since email might not be configured.

1. Click **POST /auth/reset-password**
2. Click **"Try it out"**
3. Use the token from logs:
```json
{
  "token": "RESET_TOKEN_FROM_CONSOLE",
  "newPassword": "newpassword123"
}
```
4. Click **"Execute"**
5. ✅ **Expected**: 200 response with "Password reset successfully!"

---

### **Step 10: Test Logout**

#### 🚪 Logout User
1. Click **POST /auth/signout**
2. Click **"Try it out"**
3. Use your refresh token:
```json
{
  "refreshToken": "YOUR_REFRESH_TOKEN"
}
```
4. Click **"Execute"**
5. ✅ **Expected**: 200 response with "Log out successful!"

#### ❌ Test Token After Logout
1. Try using the same refresh token again
2. ✅ **Expected**: Error - token should be invalidated

---

## 🛡️ Security Features to Test

### Rate Limiting
1. Rapidly call **POST /auth/signin** multiple times (>10 times in a minute)
2. ✅ **Expected**: 429 Too Many Requests after 10 attempts

### CORS Testing (if testing from different origin)
- Requests from allowed origins should work
- Requests from unauthorized origins should be blocked

### Input Validation
1. Try registering with invalid email format
2. Try short passwords (< 6 characters)
3. ✅ **Expected**: 400 Bad Request with validation errors

---

## 📊 Expected Response Codes Summary

| Endpoint | Method | Auth Required | Expected Success Code |
|----------|--------|---------------|----------------------|
| /auth/signup | POST | No | 200 |
| /auth/signin | POST | No | 200 |
| /auth/refreshtoken | POST | No | 200 |
| /auth/signout | POST | No | 200 |
| /auth/forgot-password | POST | No | 200 |
| /auth/reset-password | POST | No | 200 |
| /users/me | GET | Yes (USER/ADMIN) | 200 |
| /users/me | PUT | Yes (USER/ADMIN) | 200 |
| /users | GET | Yes (ADMIN) | 200 |
| /users/{id} | GET | Yes (ADMIN) | 200 |
| /users/{id} | PUT | Yes (ADMIN) | 200 |
| /users/{id} | DELETE | Yes (ADMIN) | 200 |
| /test/public | GET | No | 200 |
| /test/user | GET | Yes (USER/ADMIN) | 200 |
| /test/admin | GET | Yes (ADMIN) | 200 |

---

## 🔍 Troubleshooting

### Common Issues:

1. **401 Unauthorized**
   - Check if you've set the Bearer token correctly
   - Verify token hasn't expired (24 hours default)

2. **403 Forbidden**
   - Check if your user has the required role
   - Admin endpoints require ADMIN role

3. **422 Validation Error**
   - Check request body format
   - Ensure required fields are present

4. **429 Too Many Requests**
   - Wait a minute before trying again
   - Rate limiting is working correctly

---

## 🎯 Test Scenarios Checklist

- [ ] Public endpoint access
- [ ] User registration (success & duplicate)
- [ ] User login (success & failure)
- [ ] Token-based authentication setup
- [ ] Current user info retrieval
- [ ] User profile updates
- [ ] Admin user management
- [ ] Role-based access control
- [ ] Token refresh mechanism
- [ ] Password recovery flow
- [ ] User logout
- [ ] Rate limiting behavior
- [ ] Input validation
- [ ] Permission boundaries

---

**🎉 Congratulations!** You've successfully tested all endpoints of the Authentication & Authorization System. The API is now fully validated and ready for integration with frontend applications.

## 📝 Next Steps for Frontend Integration

1. **Save Tokens**: Store JWT tokens securely (localStorage/sessionStorage)
2. **Auto-Refresh**: Implement automatic token refresh before expiration
3. **Error Handling**: Handle 401/403 responses appropriately
4. **Route Guards**: Implement route protection based on user roles
5. **Logout**: Clear tokens on logout