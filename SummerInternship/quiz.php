<!DOCTYPE html>
<html lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="css/quiz.css">
        <link rel="icon" href="https://i.pinimg.com/564x/b1/9d/ba/b19dbaf0bb959d8b00ae0e21a2557167.jpg">
        <title>Jamming</title>
    </head>
    <body>
       <div class="container">
           <div class="title">
               <h1>Welcome the <br>FORM!</h1>
           </div>

           <div class="form">

               <div class="topHalf">

                   <div class="questions">

                       <div class="content">
                           <div class="contentText">
                               <p class="question">Tell us your first name.</p>
                               <input id="firstname" class="textBox" type="text" autocomplete="off" placeholder="Ex. Loka">
                           </div>
                       </div>

                       <div class="content">
                           <div class="contentText">
                               <p class="question">How about your last name?</p>
                               <input id="lastname" class="textBox" type="text" autocomplete="off" placeholder="Ex. Paul">
                           </div>
                       </div>

                       <div class="content">
                           <div class="contentText">
                               <p class="question">Where should we email you?</p>
                               <input id="email" class="textBox" type="text" autocomplete="off" placeholder="Ex. test@hotmail.com">
                           </div>
                       </div>

                       <div class="content">
                           <div class="contentText">
                               <p class="question">Want to text? Tell us your number.</p>
                               <input id="number" class="textBox" type="text" autocomplete="off" placeholder="Ex. 40743192143">
                           </div>
                       </div>

                   </div>

                   <div class="questions">

                       <div class="content">
                           <div class="contentText">
                               <p class="question">How young are you?</p>
                               <select class="textBox" style="width: auto" id="options" name="age"></select>
                           </div>
                       </div>

                       <div class="content">
                           <div class="contentText">
                               <p class="question">Sex</p>
                               <form>
                                   <input checked="checked" class="radio" type="radio" name="gender" value="Male"><label class="gender">Male</label>
                                   <input class="radio" type="radio" name="gender" value="Female"><label class="gender">Female</label>
                                   <input class="radio" type="radio" name="gender" value="Other"><label class="gender">Other</label>
                               </form>
                           </div>
                       </div>

                       <div class="content">
                           <div class="contentText">
                               <p class="question">Size</p>
                               <input id="size" class="textBox" type="text" autocomplete="off" placeholder="Sizes: XS, S, M, L, XL, XXL">
                           </div>
                       </div>

                       <div class="content">
                           <div class="contentText">
                               <p class="question">What is the name of your street?</p>
                               <input id="address" class="textBox" type="text" autocomplete="off" placeholder="Ex. Caramidariei, nr. 5">
                           </div>
                       </div>

                   </div>

               </div>

               <div class="bottomHalf">

                   <div class="split">
                       <p id="message">&nbsp;</p>
                       <label class="upload">
                           <input type="file" name="image" id="image" style="display: none"/>
                           <i class="button" id="button">Choose an image</i>
                           <br>
                           <i id="selected-file">&nbsp;</i>
                       </label>
                       <br>
                       <button id="submit" type="button">Submit</button>
                       <br>
                   </div>

               </div>

           </div>

       </div>

        <div class="footer">
            <p>Tell Us</p>
            <p>More!!</p>
        </div>

       <script src="javascript/jquery.js"></script>
       <script type="text/javascript" src="javascript/quiz.js"></script>

    </body>

</html>