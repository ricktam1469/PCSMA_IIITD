function validateForm() {
    var x = document.forms["soccer"]["club"].value;
    if (x == null || x == "") {
        alert("Club Name can't be empty");
        return false;
    }
    var x = document.forms["soccer"]["team1"].value;
    if (x == null || x == "") {
        alert("Team1 Name can't be empty");
        return false;
    }
    
    var x = document.forms["soccer"]["team1Goals"].value;
    if (x == null || x == "") {
        alert("Goals can't be empty");
        return false;
    }
    
    var x = document.forms["soccer"]["team2"].value;
    if (x == null || x == "") {
        alert("Team2 Name can't be empty");
        return false;
    }
    
    var x = document.forms["soccer"]["team2Goals"].value;
    if (x == null || x == "") {
        alert("Goals can't be empty");
        return false;
    }
    
    var x = document.forms["soccer"]["winner"].value;
    if (x == null || x == "") {
        alert("Winner Name can't be empty");
        return false;
    }
    
    
    var x = document.forms["soccer"]["date"].value;
    if (x == null || x == "") {
        alert("Date field can't be empty");
        return false;
    }
    
    var x = document.forms["soccer"]["playerofMatch"].value;
    if (x == null || x == "") {
        alert("Player Of Match field can't be empty");
        return false;
    }
}