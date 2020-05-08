var betAmount =0;
var money = 50;
            
/*Function that is called when the spin button is clicked*/
function Spin(){
    let input = document.getElementsByTagName("input");//This gets the value of the input. 
    for(let i = 0; i <= input.length; i++){
        if(input[i].checked){
            betAmount = parseInt(input[i].value);
            break;
        }
    }
    /*Compares the current with the button amount that is choosed*/
    if(money < betAmount){
        alert("Not enough money!");//Pop-up window if there is no more money avaiable*/
    }
    else{
        checkWinner();//Calls the function checkWinner()
    }
}
            
/*Function that checks the images in three slots are equal and change them randomly*/
function checkWinner(){
    console.log(betAmount);
    
    var images = new Array("img/lemon.png","img/num7.png","img/cherry.png","img/heart.png", "img/grape.png", "img/orange.png","img/strawberry.png");//Puts all images into an array
    
    var slot1 = Math.floor(Math.random()*images.length);//Choose a random image for slot1
    if(slot1 == 0){
        slot1=1;
    }
    document.getElementById("img1").src = images[slot1];//Image is choosed and put into slot1
    
    var slot2 = Math.floor(Math.random()*images.length);
    if(slot2 == 0){
        slot2=1;
    }
    document.getElementById("img2").src = images[slot2];
            
    var slot3 = Math.floor(Math.random()*images.length);
    if(slot3 == 0){
        slot3 = 1;
    }
    document.getElementById("img3").src = images[slot3];
    
    /*Compares the images with all three slots, if same add the money by multiple of 15*/
    if(slot1 === slot2 && slot2 === slot3){
        money+= (15*betAmount);
        setTimeout(function(){
            alert("Congratulations!");
        }, 300);
    }
    else{
        money -= betAmount;
    }
    document.getElementById("money").innerHTML = "<b>You have $" + money +"</b>";//display the money player has
}