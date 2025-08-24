package com.buildtogether.main;

import com.buildtogether.pojo.*;
import com.buildtogether.service.*;
import com.buildtogether.util.DBConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BuildTogetherApp {
    
    private static UserService userService;
    private static HackathonService hackathonService;
    private static TeamService teamService;
    private static TeamMemberService teamMemberService;
    private static JoinRequestService joinRequestService;
    private static SkillService skillService;
    private static UserSkillService userSkillService;
    private static SubmissionService submissionService;
    private static User currentUser;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        System.out.println("=== BuildTogether - Hackathon Management System ===");
        System.out.println("Welcome to the BuildTogether platform!");
        
        userService = new UserService();
        hackathonService = new HackathonService();
        teamService = new TeamService();
        teamMemberService = new TeamMemberService();
        joinRequestService = new JoinRequestService();
        skillService = new SkillService();
        userSkillService = new UserSkillService();
        submissionService = new SubmissionService();
        scanner = new Scanner(System.in);
        
        if (!testDatabaseConnection()) {
            System.out.println("Failed to connect to database. Please check your configuration.");
            return;
        }
        
        showMainMenu();
    }
    
    private static boolean testDatabaseConnection() {
        try {
            DBConnection dbConnection = DBConnection.getInstance();
            boolean isConnected = dbConnection.testConnection();
            if (isConnected) {
                System.out.println("Database connection successful!");
                return true;
            } else {
                System.out.println("Database connection failed!");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error testing database connection: " + e.getMessage());
            return false;
        }
    }
    
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Thank you for using BuildTogether!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void login() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        currentUser = userService.loginUser(email, password);
        
        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getName() + "!");
            showUserMenu();
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }
    
    private static void register() {
        System.out.println("\n=== REGISTRATION ===");
        
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter phone number (optional): ");
        String phone = scanner.nextLine();
        if (phone.trim().isEmpty()) {
            phone = null;
        }
        
        System.out.print("Enter role (e.g., Developer, Designer, etc.): ");
        String role = scanner.nextLine();
        
        System.out.print("Enter GitHub link (optional): ");
        String githubLink = scanner.nextLine();
        if (githubLink.trim().isEmpty()) {
            githubLink = null;
        }
        
        System.out.print("Enter LinkedIn link (optional): ");
        String linkedinLink = scanner.nextLine();
        if (linkedinLink.trim().isEmpty()) {
            linkedinLink = null;
        }
        
        User newUser = new User(name, email, password, phone, role, githubLink, linkedinLink);
        String result = userService.registerUser(newUser);
        System.out.println(result);
    }
    
    private static void showUserMenu() {
        while (true) {
            System.out.println("\n=== USER MENU ===");
            System.out.println("Current User: " + currentUser.getName() + " (" + currentUser.getRole() + ")");
            System.out.println("1. User Management");
            System.out.println("2. Team Management");
            System.out.println("3. Hackathon Management");
            System.out.println("4. Skill Management");
            System.out.println("5. Join Request Management");
            System.out.println("6. Submission Management");
            System.out.println("7. View Profile");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    showUserManagementMenu();
                    break;
                case 2:
                    showTeamManagementMenu();
                    break;
                case 3:
                    showHackathonManagementMenu();
                    break;
                case 4:
                    showSkillManagementMenu();
                    break;
                case 5:
                    showJoinRequestManagementMenu();
                    break;
                case 6:
                    showSubmissionManagementMenu();
                    break;
                case 7:
                    viewProfile();
                    break;
                case 8:
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void showUserManagementMenu() {
        while (true) {
            System.out.println("\n=== USER MANAGEMENT ===");
            System.out.println("1. View All Users");
            System.out.println("2. View Users by Role");
            System.out.println("3. Search User by ID");
            System.out.println("4. Search User by Email");
            System.out.println("5. Search Users by Name");
            System.out.println("6. Update Profile");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    viewUsersByRole();
                    break;
                case 3:
                    searchUserById();
                    break;
                case 4:
                    searchUserByEmail();
                    break;
                case 5:
                    searchUsersByName();
                    break;
                case 6:
                    updateProfile();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void viewAllUsers() {
        System.out.println("\n=== ALL USERS ===");
        List<User> users = userService.getAllUsers();
        
        if (users == null || users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        
        for (User user : users) {
            displayUserInfo(user);
        }
    }
    
    private static void viewUsersByRole() {
        System.out.println("\n=== USERS BY ROLE ===");
        System.out.print("Enter role to search for: ");
        String role = scanner.nextLine();
        
        List<User> users = userService.getUsersByRole(role);
        
        if (users == null || users.isEmpty()) {
            System.out.println("No users found with role: " + role);
            return;
        }
        
        System.out.println("Users with role '" + role + "':");
        for (User user : users) {
            displayUserInfo(user);
        }
    }
    
    private static void searchUserById() {
        System.out.println("\n=== SEARCH USER BY ID ===");
        System.out.print("Enter user ID: ");
        int userId = getIntInput();
        
        User user = userService.getUserById(userId);
        
        if (user == null) {
            System.out.println("User not found with ID: " + userId);
            return;
        }
        
        System.out.println("User found:");
        displayUserInfo(user);
    }
    
    private static void searchUserByEmail() {
        System.out.println("\n=== SEARCH USER BY EMAIL ===");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        User user = userService.getUserByEmail(email);
        
        if (user == null) {
            System.out.println("User not found with email: " + email);
            return;
        }
        
        System.out.println("User found:");
        displayUserInfo(user);
    }
    
    private static void searchUsersByName() {
        System.out.println("\n=== SEARCH USERS BY NAME ===");
        System.out.print("Enter name (partial match): ");
        String name = scanner.nextLine();
        
        if (name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        
        List<User> users = userService.searchUsersByName(name);
        
        if (users == null || users.isEmpty()) {
            System.out.println("No users found matching: " + name);
            return;
        }
        
        System.out.println("Users matching '" + name + "':");
        for (User user : users) {
            displayUserInfo(user);
        }
    }
    
    private static void updateProfile() {
        System.out.println("\n=== UPDATE PROFILE ===");
        
        System.out.print("Enter new name (current: " + currentUser.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            currentUser.setName(name);
        }
        
        System.out.print("Enter new email (current: " + currentUser.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) {
            currentUser.setEmail(email);
        }
        
        System.out.print("Enter new phone (current: " + (currentUser.getPhone() != null ? currentUser.getPhone() : "Not set") + "): ");
        String phone = scanner.nextLine();
        if (!phone.trim().isEmpty()) {
            currentUser.setPhone(phone);
        }
        
        System.out.print("Enter new role (current: " + (currentUser.getRole() != null ? currentUser.getRole() : "Not set") + "): ");
        String role = scanner.nextLine();
        if (!role.trim().isEmpty()) {
            currentUser.setRole(role);
        }
        
        System.out.print("Enter new GitHub link (current: " + (currentUser.getGithubLink() != null ? currentUser.getGithubLink() : "Not set") + "): ");
        String githubLink = scanner.nextLine();
        if (!githubLink.trim().isEmpty()) {
            currentUser.setGithubLink(githubLink);
        }
        
        System.out.print("Enter new LinkedIn link (current: " + (currentUser.getLinkedinLink() != null ? currentUser.getLinkedinLink() : "Not set") + "): ");
        String linkedinLink = scanner.nextLine();
        if (!linkedinLink.trim().isEmpty()) {
            currentUser.setLinkedinLink(linkedinLink);
        }
        
        String result = userService.updateUserProfile(currentUser);
        System.out.println(result);
    }
    
    private static void viewProfile() {
        System.out.println("\n=== YOUR PROFILE ===");
        displayUserInfo(currentUser);
    }
    
    private static void displayUserInfo(User user) {
        System.out.println("User ID: " + user.getUserId());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone: " + (user.getPhone() != null ? user.getPhone() : "Not provided"));
        System.out.println("Role: " + (user.getRole() != null ? user.getRole() : "Not specified"));
        System.out.println("GitHub: " + (user.getGithubLink() != null ? user.getGithubLink() : "Not provided"));
        System.out.println("LinkedIn: " + (user.getLinkedinLink() != null ? user.getLinkedinLink() : "Not provided"));
        System.out.println("---");
    }
    
    private static void showTeamManagementMenu() {
        while (true) {
            System.out.println("\n=== TEAM MANAGEMENT ===");
            System.out.println("1. View All Teams");
            System.out.println("2. View My Teams");
            System.out.println("3. Create New Team");
            System.out.println("4. View Team Details");
            System.out.println("5. Search Teams by Name");
            System.out.println("6. View Teams with Available Spots");
            System.out.println("7. Manage Team Members");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewAllTeams();
                    break;
                case 2:
                    viewMyTeams();
                    break;
                case 3:
                    createNewTeam();
                    break;
                case 4:
                    viewTeamDetails();
                    break;
                case 5:
                    searchTeamsByName();
                    break;
                case 6:
                    viewTeamsWithAvailableSpots();
                    break;
                case 7:
                    manageTeamMembers();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void showHackathonManagementMenu() {
        while (true) {
            System.out.println("\n=== HACKATHON MANAGEMENT ===");
            System.out.println("1. View All Hackathons");
            System.out.println("2. View Upcoming Hackathons");
            System.out.println("3. View Active Hackathons");
            System.out.println("4. View Completed Hackathons");
            System.out.println("5. Create New Hackathon");
            System.out.println("6. View Hackathon Details");
            System.out.println("7. Search Hackathons by Name");
            System.out.println("8. Search Hackathons by Theme");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewAllHackathons();
                    break;
                case 2:
                    viewUpcomingHackathons();
                    break;
                case 3:
                    viewActiveHackathons();
                    break;
                case 4:
                    viewCompletedHackathons();
                    break;
                case 5:
                    createNewHackathon();
                    break;
                case 6:
                    viewHackathonDetails();
                    break;
                case 7:
                    searchHackathonsByName();
                    break;
                case 8:
                    searchHackathonsByTheme();
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void showSkillManagementMenu() {
        while (true) {
            System.out.println("\n=== SKILL MANAGEMENT ===");
            System.out.println("1. View All Skills");
            System.out.println("2. View My Skills");
            System.out.println("3. Add Skill to Profile");
            System.out.println("4. Update Skill Proficiency");
            System.out.println("5. Remove Skill from Profile");
            System.out.println("6. Search Skills by Name");
            System.out.println("7. View Popular Skills");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewAllSkills();
                    break;
                case 2:
                    viewMySkills();
                    break;
                case 3:
                    addSkillToProfile();
                    break;
                case 4:
                    updateSkillProficiency();
                    break;
                case 5:
                    removeSkillFromProfile();
                    break;
                case 6:
                    searchSkillsByName();
                    break;
                case 7:
                    viewPopularSkills();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void showJoinRequestManagementMenu() {
        while (true) {
            System.out.println("\n=== JOIN REQUEST MANAGEMENT ===");
            System.out.println("1. View My Join Requests");
            System.out.println("2. View Pending Requests for My Teams");
            System.out.println("3. Send Join Request");
            System.out.println("4. Approve Join Request");
            System.out.println("5. Reject Join Request");
            System.out.println("6. Cancel Join Request");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewMyJoinRequests();
                    break;
                case 2:
                    viewPendingRequestsForMyTeams();
                    break;
                case 3:
                    sendJoinRequest();
                    break;
                case 4:
                    approveJoinRequest();
                    break;
                case 5:
                    rejectJoinRequest();
                    break;
                case 6:
                    cancelJoinRequest();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAllTeams() {
        System.out.println("\n=== ALL TEAMS ===");
        List<Team> teams = teamService.getAllTeams();
        
        if (teams == null || teams.isEmpty()) {
            System.out.println("No teams found.");
            return;
        }
        
        for (Team team : teams) {
            displayTeamInfo(team);
        }
    }
    
    private static void viewMyTeams() {
        System.out.println("\n=== MY TEAMS ===");
        List<Team> teams = teamService.getTeamsByLeaderId(currentUser.getUserId());
        
        if (teams == null || teams.isEmpty()) {
            System.out.println("You haven't created any teams yet.");
            return;
        }
        
        for (Team team : teams) {
            displayTeamInfo(team);
        }
    }
    
    private static void createNewTeam() {
        System.out.println("\n=== CREATE NEW TEAM ===");
        
        // First, show available hackathons
        System.out.println("Available Hackathons:");
        List<Hackathon> hackathons = hackathonService.getAllHackathons();
        if (hackathons == null || hackathons.isEmpty()) {
            System.out.println("No hackathons available.");
            return;
        }
        
        for (Hackathon hackathon : hackathons) {
            System.out.println("ID: " + hackathon.getHackathonId() + " - " + hackathon.getTitle());
        }
        
        System.out.print("Enter hackathon ID: ");
        int hackathonId = getIntInput();
        
        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();
        
        Team team = new Team();
        team.setHackathonId(hackathonId);
        team.setCreatedBy(currentUser.getUserId());
        team.setTeamName(teamName);
        
        String result = teamService.createTeam(team);
        System.out.println(result);
    }
    
    private static void viewTeamDetails() {
        System.out.println("\n=== VIEW TEAM DETAILS ===");
        System.out.print("Enter team ID: ");
        int teamId = getIntInput();
        
        Team team = teamService.getTeamById(teamId);
        if (team == null) {
            System.out.println("Team not found.");
            return;
        }
        
        displayTeamInfo(team);
        
        // Show team members
        System.out.println("\nTeam Members:");
        List<TeamMember> members = teamMemberService.getTeamMembersByTeamId(teamId);
        if (members != null && !members.isEmpty()) {
            for (TeamMember member : members) {
                User user = userService.getUserById(member.getUserId());
                if (user != null) {
                    System.out.println("- " + user.getName() + " (" + member.getRoleInTeam() + ")");
                }
            }
        } else {
            System.out.println("No members found.");
        }
    }
    
    private static void searchTeamsByName() {
        System.out.println("\n=== SEARCH TEAMS BY NAME ===");
        System.out.print("Enter team name (partial match): ");
        String teamName = scanner.nextLine();
        
        if (teamName.trim().isEmpty()) {
            System.out.println("Team name cannot be empty.");
            return;
        }
        
        List<Team> teams = teamService.searchTeamsByName(teamName);
        
        if (teams == null || teams.isEmpty()) {
            System.out.println("No teams found matching: " + teamName);
            return;
        }
        
        System.out.println("Teams matching '" + teamName + "':");
        for (Team team : teams) {
            displayTeamInfo(team);
        }
    }
    
    private static void viewTeamsWithAvailableSpots() {
        System.out.println("\n=== TEAMS WITH AVAILABLE SPOTS ===");
        List<Team> teams = teamService.getTeamsWithAvailableSpots();
        
        if (teams == null || teams.isEmpty()) {
            System.out.println("No teams with available spots found.");
            return;
        }
        
        for (Team team : teams) {
            displayTeamInfo(team);
        }
    }
    
    private static void manageTeamMembers() {
        System.out.println("\n=== MANAGE TEAM MEMBERS ===");
        System.out.println("1. Add Member to Team");
        System.out.println("2. Remove Member from Team");
        System.out.println("3. Update Member Role");
        System.out.println("4. Back to Team Management");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                addMemberToTeam();
                break;
            case 2:
                removeMemberFromTeam();
                break;
            case 3:
                updateMemberRole();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void addMemberToTeam() {
        System.out.println("\n=== ADD MEMBER TO TEAM ===");
        System.out.print("Enter team ID: ");
        int teamId = getIntInput();
        
        System.out.print("Enter user ID: ");
        int userId = getIntInput();
        
        System.out.print("Enter role in team: ");
        String role = scanner.nextLine();
        
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamId(teamId);
        teamMember.setUserId(userId);
        teamMember.setRoleInTeam(role);
        
        String result = teamMemberService.addTeamMember(teamMember);
        System.out.println(result);
    }
    
    private static void removeMemberFromTeam() {
        System.out.println("\n=== REMOVE MEMBER FROM TEAM ===");
        System.out.print("Enter team ID: ");
        int teamId = getIntInput();
        
        System.out.print("Enter user ID: ");
        int userId = getIntInput();
        
        String result = teamMemberService.removeUserFromTeam(userId, teamId);
        System.out.println(result);
    }
    
    private static void updateMemberRole() {
        System.out.println("\n=== UPDATE MEMBER ROLE ===");
        System.out.print("Enter team ID: ");
        int teamId = getIntInput();
        
        System.out.print("Enter user ID: ");
        int userId = getIntInput();
        
        System.out.print("Enter new role: ");
        String newRole = scanner.nextLine();
        
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamId(teamId);
        teamMember.setUserId(userId);
        teamMember.setRoleInTeam(newRole);
        
        String result = teamMemberService.updateTeamMember(teamMember);
        System.out.println(result);
    }
    
    private static void displayTeamInfo(Team team) {
        System.out.println("Team ID: " + team.getTeamId());
        System.out.println("Team Name: " + team.getTeamName());
        System.out.println("Hackathon ID: " + team.getHackathonId());
        System.out.println("Created By: " + team.getCreatedBy());
        System.out.println("---");
    }

    private static void viewAllHackathons() {
        System.out.println("\n=== ALL HACKATHONS ===");
        List<Hackathon> hackathons = hackathonService.getAllHackathons();
        
        if (hackathons == null || hackathons.isEmpty()) {
            System.out.println("No hackathons found.");
            return;
        }
        
        for (Hackathon hackathon : hackathons) {
            displayHackathonInfo(hackathon);
        }
    }
    
    private static void viewUpcomingHackathons() {
        System.out.println("\n=== UPCOMING HACKATHONS ===");
        List<Hackathon> hackathons = hackathonService.getUpcomingHackathons();
        
        if (hackathons == null || hackathons.isEmpty()) {
            System.out.println("No upcoming hackathons found.");
            return;
        }
        
        for (Hackathon hackathon : hackathons) {
            displayHackathonInfo(hackathon);
        }
    }
    
    private static void viewActiveHackathons() {
        System.out.println("\n=== ACTIVE HACKATHONS ===");
        List<Hackathon> hackathons = hackathonService.getActiveHackathons();
        
        if (hackathons == null || hackathons.isEmpty()) {
            System.out.println("No active hackathons found.");
            return;
        }
        
        for (Hackathon hackathon : hackathons) {
            displayHackathonInfo(hackathon);
        }
    }
    
    private static void viewCompletedHackathons() {
        System.out.println("\n=== COMPLETED HACKATHONS ===");
        List<Hackathon> hackathons = hackathonService.getCompletedHackathons();
        
        if (hackathons == null || hackathons.isEmpty()) {
            System.out.println("No completed hackathons found.");
            return;
        }
        
        for (Hackathon hackathon : hackathons) {
            displayHackathonInfo(hackathon);
        }
    }
    
    private static void createNewHackathon() {
        System.out.println("\n=== CREATE NEW HACKATHON ===");
        
        System.out.print("Enter hackathon title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine();
        
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDateStr = scanner.nextLine();
        
        System.out.print("Enter max team size: ");
        int maxTeamSize = getIntInput();
        
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);
            
            Hackathon hackathon = new Hackathon();
            hackathon.setTitle(title);
            hackathon.setDescription(description);
            hackathon.setStartDate(startDate);
            hackathon.setEndDate(endDate);
            hackathon.setMaxTeamSize(maxTeamSize);
            
            String result = hackathonService.createHackathon(hackathon);
            System.out.println(result);
            
        } catch (Exception e) {
            System.out.println("Error creating hackathon: " + e.getMessage());
        }
    }
    
    private static void viewHackathonDetails() {
        System.out.println("\n=== VIEW HACKATHON DETAILS ===");
        System.out.print("Enter hackathon ID: ");
        int hackathonId = getIntInput();
        
        Hackathon hackathon = hackathonService.getHackathonById(hackathonId);
        if (hackathon == null) {
            System.out.println("Hackathon not found.");
            return;
        }
        
        displayHackathonInfo(hackathon);
        
        // Show teams for this hackathon
        System.out.println("\nTeams in this hackathon:");
        List<Team> teams = teamService.getTeamsByHackathonId(hackathonId);
        if (teams != null && !teams.isEmpty()) {
            for (Team team : teams) {
                displayTeamInfo(team);
            }
        } else {
            System.out.println("No teams found for this hackathon.");
        }
    }
    
    private static void searchHackathonsByName() {
        System.out.println("\n=== SEARCH HACKATHONS BY NAME ===");
        System.out.print("Enter hackathon name (partial match): ");
        String name = scanner.nextLine();
        
        if (name.trim().isEmpty()) {
            System.out.println("Hackathon name cannot be empty.");
            return;
        }
        
        List<Hackathon> hackathons = hackathonService.searchHackathonsByName(name);
        
        if (hackathons == null || hackathons.isEmpty()) {
            System.out.println("No hackathons found matching: " + name);
            return;
        }
        
        System.out.println("Hackathons matching '" + name + "':");
        for (Hackathon hackathon : hackathons) {
            displayHackathonInfo(hackathon);
        }
    }
    
    private static void searchHackathonsByTheme() {
        System.out.println("\n=== SEARCH HACKATHONS BY THEME ===");
        System.out.print("Enter theme (partial match): ");
        String theme = scanner.nextLine();
        
        if (theme.trim().isEmpty()) {
            System.out.println("Theme cannot be empty.");
            return;
        }
        
        List<Hackathon> hackathons = hackathonService.getHackathonsByTheme(theme);
        
        if (hackathons == null || hackathons.isEmpty()) {
            System.out.println("No hackathons found matching theme: " + theme);
            return;
        }
        
        System.out.println("Hackathons matching theme '" + theme + "':");
        for (Hackathon hackathon : hackathons) {
            displayHackathonInfo(hackathon);
        }
    }
    
    private static void displayHackathonInfo(Hackathon hackathon) {
        System.out.println("Hackathon ID: " + hackathon.getHackathonId());
        System.out.println("Title: " + hackathon.getTitle());
        System.out.println("Description: " + hackathon.getDescription());
        System.out.println("Start Date: " + hackathon.getStartDate());
        System.out.println("End Date: " + hackathon.getEndDate());
        System.out.println("Max Team Size: " + hackathon.getMaxTeamSize());
        System.out.println("Status: " + hackathonService.getHackathonStatus(hackathon));
        System.out.println("---");
    }

    private static void viewAllSkills() {
        System.out.println("\n=== ALL SKILLS ===");
        List<Skill> skills = skillService.getAllSkills();
        
        if (skills == null || skills.isEmpty()) {
            System.out.println("No skills found.");
            return;
        }
        
        for (Skill skill : skills) {
            displaySkillInfo(skill);
        }
    }
    
    private static void viewMySkills() {
        System.out.println("\n=== MY SKILLS ===");
        List<UserSkill> userSkills = userSkillService.getUserSkillsByUserId(currentUser.getUserId());
        
        if (userSkills == null || userSkills.isEmpty()) {
            System.out.println("You haven't added any skills to your profile yet.");
            return;
        }
        
        for (UserSkill userSkill : userSkills) {
            Skill skill = skillService.getSkillById(userSkill.getSkillId());
            if (skill != null) {
                System.out.println("Skill: " + skill.getSkillName() + " - Level: " + userSkill.getProficiencyLevel());
            }
        }
    }
    
    private static void addSkillToProfile() {
        System.out.println("\n=== ADD SKILL TO PROFILE ===");
        
        // Show available skills
        System.out.println("Available Skills:");
        List<Skill> skills = skillService.getAllSkills();
        if (skills == null || skills.isEmpty()) {
            System.out.println("No skills available.");
            return;
        }
        
        for (Skill skill : skills) {
            System.out.println("ID: " + skill.getSkillId() + " - " + skill.getSkillName());
        }
        
        System.out.print("Enter skill ID: ");
        int skillId = getIntInput();
        
        System.out.println("Proficiency Levels: BEGINNER, INTERMEDIATE, ADVANCED, EXPERT");
        System.out.print("Enter proficiency level: ");
        String proficiencyLevel = scanner.nextLine().toUpperCase();
        
        UserSkill userSkill = new UserSkill();
        userSkill.setUserId(currentUser.getUserId());
        userSkill.setSkillId(skillId);
        userSkill.setProficiencyLevel(proficiencyLevel);
        
        String result = userSkillService.addUserSkill(userSkill);
        System.out.println(result);
    }
    
    private static void updateSkillProficiency() {
        System.out.println("\n=== UPDATE SKILL PROFICIENCY ===");
        
        // Show user's skills
        System.out.println("Your Skills:");
        List<UserSkill> userSkills = userSkillService.getUserSkillsByUserId(currentUser.getUserId());
        if (userSkills == null || userSkills.isEmpty()) {
            System.out.println("You haven't added any skills to your profile yet.");
            return;
        }
        
        for (UserSkill userSkill : userSkills) {
            Skill skill = skillService.getSkillById(userSkill.getSkillId());
            if (skill != null) {
                System.out.println("ID: " + userSkill.getSkillId() + " - " + skill.getSkillName() + " (Current: " + userSkill.getProficiencyLevel() + ")");
            }
        }
        
        System.out.print("Enter skill ID: ");
        int skillId = getIntInput();
        
        System.out.println("Proficiency Levels: BEGINNER, INTERMEDIATE, ADVANCED, EXPERT");
        System.out.print("Enter new proficiency level: ");
        String proficiencyLevel = scanner.nextLine().toUpperCase();
        
        UserSkill userSkill = new UserSkill();
        userSkill.setUserId(currentUser.getUserId());
        userSkill.setSkillId(skillId);
        userSkill.setProficiencyLevel(proficiencyLevel);
        
        String result = userSkillService.updateUserSkill(userSkill);
        System.out.println(result);
    }
    
    private static void removeSkillFromProfile() {
        System.out.println("\n=== REMOVE SKILL FROM PROFILE ===");
        
        // Show user's skills
        System.out.println("Your Skills:");
        List<UserSkill> userSkills = userSkillService.getUserSkillsByUserId(currentUser.getUserId());
        if (userSkills == null || userSkills.isEmpty()) {
            System.out.println("You haven't added any skills to your profile yet.");
            return;
        }
        
        for (UserSkill userSkill : userSkills) {
            Skill skill = skillService.getSkillById(userSkill.getSkillId());
            if (skill != null) {
                System.out.println("ID: " + userSkill.getSkillId() + " - " + skill.getSkillName());
            }
        }
        
        System.out.print("Enter skill ID to remove: ");
        int skillId = getIntInput();
        
        String result = userSkillService.removeUserSkill(currentUser.getUserId(), skillId);
        System.out.println(result);
    }
    
    private static void searchSkillsByName() {
        System.out.println("\n=== SEARCH SKILLS BY NAME ===");
        System.out.print("Enter skill name (partial match): ");
        String skillName = scanner.nextLine();
        
        if (skillName.trim().isEmpty()) {
            System.out.println("Skill name cannot be empty.");
            return;
        }
        
        List<Skill> skills = skillService.searchSkillsByName(skillName);
        
        if (skills == null || skills.isEmpty()) {
            System.out.println("No skills found matching: " + skillName);
            return;
        }
        
        System.out.println("Skills matching '" + skillName + "':");
        for (Skill skill : skills) {
            displaySkillInfo(skill);
        }
    }
    
    private static void viewPopularSkills() {
        System.out.println("\n=== POPULAR SKILLS ===");
        List<Skill> skills = skillService.getPopularSkills(10);
        
        if (skills == null || skills.isEmpty()) {
            System.out.println("No skills found.");
            return;
        }
        
        for (Skill skill : skills) {
            displaySkillInfo(skill);
        }
    }
    
    private static void displaySkillInfo(Skill skill) {
        System.out.println("Skill ID: " + skill.getSkillId());
        System.out.println("Skill Name: " + skill.getSkillName());
        System.out.println("---");
    }

    private static void viewMyJoinRequests() {
        System.out.println("\n=== MY JOIN REQUESTS ===");
        List<JoinRequest> joinRequests = joinRequestService.getJoinRequestsByUserId(currentUser.getUserId());
        
        if (joinRequests == null || joinRequests.isEmpty()) {
            System.out.println("You haven't sent any join requests.");
            return;
        }
        
        for (JoinRequest joinRequest : joinRequests) {
            displayJoinRequestInfo(joinRequest);
        }
    }
    
    private static void viewPendingRequestsForMyTeams() {
        System.out.println("\n=== PENDING REQUESTS FOR MY TEAMS ===");
        
        // Get teams created by current user
        List<Team> myTeams = teamService.getTeamsByLeaderId(currentUser.getUserId());
        if (myTeams == null || myTeams.isEmpty()) {
            System.out.println("You haven't created any teams.");
            return;
        }
        
        for (Team team : myTeams) {
            System.out.println("\nTeam: " + team.getTeamName() + " (ID: " + team.getTeamId() + ")");
            List<JoinRequest> pendingRequests = joinRequestService.getPendingJoinRequestsByTeamId(team.getTeamId());
            
            if (pendingRequests == null || pendingRequests.isEmpty()) {
                System.out.println("No pending requests.");
            } else {
                for (JoinRequest joinRequest : pendingRequests) {
                    displayJoinRequestInfo(joinRequest);
                }
            }
        }
    }
    
    private static void sendJoinRequest() {
        System.out.println("\n=== SEND JOIN REQUEST ===");
        
        // Show teams with available spots
        System.out.println("Teams with Available Spots:");
        List<Team> teams = teamService.getTeamsWithAvailableSpots();
        if (teams == null || teams.isEmpty()) {
            System.out.println("No teams with available spots found.");
            return;
        }
        
        for (Team team : teams) {
            displayTeamInfo(team);
        }
        
        System.out.print("Enter team ID: ");
        int teamId = getIntInput();
        
        // Check if user already has a pending request for this team
        if (joinRequestService.userHasPendingRequest(currentUser.getUserId(), teamId)) {
            System.out.println("You already have a pending request for this team.");
            return;
        }
        
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setTeamId(teamId);
        joinRequest.setUserId(currentUser.getUserId());
        joinRequest.setStatus("PENDING");
        
        String result = joinRequestService.createJoinRequest(joinRequest);
        System.out.println(result);
    }
    
    private static void approveJoinRequest() {
        System.out.println("\n=== APPROVE JOIN REQUEST ===");
        System.out.print("Enter join request ID: ");
        int requestId = getIntInput();
        
        String result = joinRequestService.approveJoinRequest(requestId);
        System.out.println(result);
    }
    
    private static void rejectJoinRequest() {
        System.out.println("\n=== REJECT JOIN REQUEST ===");
        System.out.print("Enter join request ID: ");
        int requestId = getIntInput();
        
        String result = joinRequestService.rejectJoinRequest(requestId);
        System.out.println(result);
    }
    
    private static void cancelJoinRequest() {
        System.out.println("\n=== CANCEL JOIN REQUEST ===");
        System.out.print("Enter join request ID: ");
        int requestId = getIntInput();
        
        String result = joinRequestService.deleteJoinRequest(requestId);
        System.out.println(result);
    }
    
    private static void displayJoinRequestInfo(JoinRequest joinRequest) {
        System.out.println("Request ID: " + joinRequest.getRequestId());
        System.out.println("Team ID: " + joinRequest.getTeamId());
        System.out.println("User ID: " + joinRequest.getUserId());
        System.out.println("Status: " + joinRequest.getStatus());
        System.out.println("Requested At: " + joinRequest.getRequestedAt());
        System.out.println("---");
    }

    private static void showSubmissionManagementMenu() {
        while (true) {
            System.out.println("\n=== SUBMISSION MANAGEMENT ===");
            System.out.println("1. Submit New Project");
            System.out.println("2. View My Submissions");
            System.out.println("3. View All Submissions");
            System.out.println("4. View Submissions by Hackathon");
            System.out.println("5. View Submissions by Status");
            System.out.println("6. View Top Submissions");
            System.out.println("7. Search Submissions by Title");
            System.out.println("8. Search Submissions by Technologies");
            System.out.println("9. Update Submission");
            System.out.println("10. Delete Submission");
            System.out.println("11. Update Submission Status (Admin/Judge)");
            System.out.println("12. Update Submission Score (Judge)");
            System.out.println("13. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    submitNewProject();
                    break;
                case 2:
                    viewMySubmissions();
                    break;
                case 3:
                    viewAllSubmissions();
                    break;
                case 4:
                    viewSubmissionsByHackathon();
                    break;
                case 5:
                    viewSubmissionsByStatus();
                    break;
                case 6:
                    viewTopSubmissions();
                    break;
                case 7:
                    searchSubmissionsByTitle();
                    break;
                case 8:
                    searchSubmissionsByTechnologies();
                    break;
                case 9:
                    updateSubmission();
                    break;
                case 10:
                    deleteSubmission();
                    break;
                case 11:
                    updateSubmissionStatus();
                    break;
                case 12:
                    updateSubmissionScore();
                    break;
                case 13:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void submitNewProject() {
        System.out.println("\n=== SUBMIT NEW PROJECT ===");
        
        // Show user's teams
        List<Team> myTeams = teamService.getTeamsByLeaderId(currentUser.getUserId());
        if (myTeams == null || myTeams.isEmpty()) {
            System.out.println("You don't have any teams. Please create a team first.");
            return;
        }
        
        System.out.println("Your Teams:");
        for (Team team : myTeams) {
            displayTeamInfo(team);
        }
        
        System.out.print("Enter team ID: ");
        int teamId = getIntInput();
        
        // Show available hackathons
        List<Hackathon> hackathons = hackathonService.getActiveHackathons();
        if (hackathons == null || hackathons.isEmpty()) {
            System.out.println("No active hackathons available for submission.");
            return;
        }
        
        System.out.println("Available Hackathons:");
        for (Hackathon hackathon : hackathons) {
            displayHackathonInfo(hackathon);
        }
        
        System.out.print("Enter hackathon ID: ");
        int hackathonId = getIntInput();
        
        // Check if team has already submitted for this hackathon
        if (submissionService.teamHasSubmitted(teamId, hackathonId)) {
            System.out.println("Your team has already submitted a project for this hackathon.");
            return;
        }
        
        System.out.print("Enter project title: ");
        String projectTitle = scanner.nextLine();
        
        System.out.print("Enter project description: ");
        String projectDescription = scanner.nextLine();
        
        System.out.print("Enter GitHub link (optional): ");
        String githubLink = scanner.nextLine();
        if (githubLink.trim().isEmpty()) githubLink = null;
        
        System.out.print("Enter demo link (optional): ");
        String demoLink = scanner.nextLine();
        if (demoLink.trim().isEmpty()) demoLink = null;
        
        System.out.print("Enter presentation link (optional): ");
        String presentationLink = scanner.nextLine();
        if (presentationLink.trim().isEmpty()) presentationLink = null;
        
        System.out.print("Enter technologies used: ");
        String technologies = scanner.nextLine();
        
        System.out.print("Enter features implemented: ");
        String features = scanner.nextLine();
        
        Submission submission = submissionService.submitProject(teamId, hackathonId, projectTitle, 
                                                               projectDescription, githubLink, demoLink, 
                                                               presentationLink, technologies, features);
        
        if (submission != null) {
            System.out.println("Project submitted successfully!");
        }
    }

    private static void viewMySubmissions() {
        System.out.println("\n=== MY SUBMISSIONS ===");
        
        // Get user's teams
        List<Team> myTeams = teamService.getTeamsByLeaderId(currentUser.getUserId());
        if (myTeams == null || myTeams.isEmpty()) {
            System.out.println("You don't have any teams.");
            return;
        }
        
        for (Team team : myTeams) {
            System.out.println("\nSubmissions for Team: " + team.getTeamName() + " (ID: " + team.getTeamId() + ")");
            List<Submission> submissions = submissionService.getSubmissionsByTeamId(team.getTeamId());
            
            if (submissions == null || submissions.isEmpty()) {
                System.out.println("No submissions found for this team.");
            } else {
                for (Submission submission : submissions) {
                    submissionService.displaySubmissionInfo(submission);
                }
            }
        }
    }

    private static void viewAllSubmissions() {
        System.out.println("\n=== ALL SUBMISSIONS ===");
        List<Submission> submissions = submissionService.getAllSubmissions();
        
        if (submissions == null || submissions.isEmpty()) {
            System.out.println("No submissions found.");
            return;
        }
        
        for (Submission submission : submissions) {
            submissionService.displaySubmissionInfo(submission);
        }
    }

    private static void viewSubmissionsByHackathon() {
        System.out.println("\n=== SUBMISSIONS BY HACKATHON ===");
        System.out.print("Enter hackathon ID: ");
        int hackathonId = getIntInput();
        
        List<Submission> submissions = submissionService.getSubmissionsByHackathonId(hackathonId);
        
        if (submissions == null || submissions.isEmpty()) {
            System.out.println("No submissions found for this hackathon.");
            return;
        }
        
        for (Submission submission : submissions) {
            submissionService.displaySubmissionInfo(submission);
        }
    }

    private static void viewSubmissionsByStatus() {
        System.out.println("\n=== SUBMISSIONS BY STATUS ===");
        System.out.println("Available statuses: SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED");
        System.out.print("Enter status: ");
        String status = scanner.nextLine();
        
        List<Submission> submissions = submissionService.getSubmissionsByStatus(status);
        
        if (submissions == null || submissions.isEmpty()) {
            System.out.println("No submissions found with status: " + status);
            return;
        }
        
        for (Submission submission : submissions) {
            submissionService.displaySubmissionInfo(submission);
        }
    }

    private static void viewTopSubmissions() {
        System.out.println("\n=== TOP SUBMISSIONS ===");
        System.out.print("Enter number of top submissions to view: ");
        int limit = getIntInput();
        
        List<Submission> submissions = submissionService.getTopSubmissions(limit);
        
        if (submissions == null || submissions.isEmpty()) {
            System.out.println("No top submissions found.");
            return;
        }
        
        for (Submission submission : submissions) {
            submissionService.displaySubmissionInfo(submission);
        }
    }

    private static void searchSubmissionsByTitle() {
        System.out.println("\n=== SEARCH SUBMISSIONS BY TITLE ===");
        System.out.print("Enter title to search for: ");
        String title = scanner.nextLine();
        
        List<Submission> submissions = submissionService.searchSubmissionsByTitle(title);
        
        if (submissions == null || submissions.isEmpty()) {
            System.out.println("No submissions found with title containing: " + title);
            return;
        }
        
        for (Submission submission : submissions) {
            submissionService.displaySubmissionInfo(submission);
        }
    }

    private static void searchSubmissionsByTechnologies() {
        System.out.println("\n=== SEARCH SUBMISSIONS BY TECHNOLOGIES ===");
        System.out.print("Enter technologies to search for: ");
        String technologies = scanner.nextLine();
        
        List<Submission> submissions = submissionService.getSubmissionsByTechnologies(technologies);
        
        if (submissions == null || submissions.isEmpty()) {
            System.out.println("No submissions found using technologies: " + technologies);
            return;
        }
        
        for (Submission submission : submissions) {
            submissionService.displaySubmissionInfo(submission);
        }
    }

    private static void updateSubmission() {
        System.out.println("\n=== UPDATE SUBMISSION ===");
        System.out.print("Enter submission ID: ");
        int submissionId = getIntInput();
        
        System.out.print("Enter new project title (or press Enter to skip): ");
        String projectTitle = scanner.nextLine();
        if (projectTitle.trim().isEmpty()) projectTitle = null;
        
        System.out.print("Enter new project description (or press Enter to skip): ");
        String projectDescription = scanner.nextLine();
        if (projectDescription.trim().isEmpty()) projectDescription = null;
        
        System.out.print("Enter new GitHub link (or press Enter to skip): ");
        String githubLink = scanner.nextLine();
        if (githubLink.trim().isEmpty()) githubLink = null;
        
        System.out.print("Enter new demo link (or press Enter to skip): ");
        String demoLink = scanner.nextLine();
        if (demoLink.trim().isEmpty()) demoLink = null;
        
        System.out.print("Enter new presentation link (or press Enter to skip): ");
        String presentationLink = scanner.nextLine();
        if (presentationLink.trim().isEmpty()) presentationLink = null;
        
        System.out.print("Enter new technologies (or press Enter to skip): ");
        String technologies = scanner.nextLine();
        if (technologies.trim().isEmpty()) technologies = null;
        
        System.out.print("Enter new features (or press Enter to skip): ");
        String features = scanner.nextLine();
        if (features.trim().isEmpty()) features = null;
        
        boolean success = submissionService.updateSubmission(submissionId, projectTitle, projectDescription,
                                                           githubLink, demoLink, presentationLink, technologies, features);
        
        if (success) {
            System.out.println("Submission updated successfully!");
        }
    }

    private static void deleteSubmission() {
        System.out.println("\n=== DELETE SUBMISSION ===");
        System.out.print("Enter submission ID: ");
        int submissionId = getIntInput();
        
        boolean success = submissionService.deleteSubmission(submissionId);
        
        if (success) {
            System.out.println("Submission deleted successfully!");
        }
    }

    private static void updateSubmissionStatus() {
        System.out.println("\n=== UPDATE SUBMISSION STATUS ===");
        System.out.print("Enter submission ID: ");
        int submissionId = getIntInput();
        
        System.out.println("Available statuses: SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED");
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();
        
        boolean success = submissionService.updateSubmissionStatus(submissionId, status);
        
        if (success) {
            System.out.println("Submission status updated successfully!");
        }
    }

    private static void updateSubmissionScore() {
        System.out.println("\n=== UPDATE SUBMISSION SCORE ===");
        System.out.print("Enter submission ID: ");
        int submissionId = getIntInput();
        
        System.out.print("Enter score (0-100): ");
        double score = getDoubleInput();
        
        System.out.print("Enter judge comments: ");
        String comments = scanner.nextLine();
        
        boolean success = submissionService.updateSubmissionScore(submissionId, score, comments);
        
        if (success) {
            System.out.println("Submission score updated successfully!");
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}