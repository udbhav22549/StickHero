READ ME

/////////////////////////////////////////////////////


This is a demo for the game STICK-HERO implemented on the javafx visual library.


/////////////////////////////////////////////////////


Backstory for the game:-

In the mystical realm of Shadowhaven, where ancient traditions and martial arts are deeply ingrained, a legendary Stick Ninja named Kuro found himself on a quest to master the art of the extendable staff. The sacred staff, known as the "Shinobu Rod," was said to possess the power to bridge the gap between worlds and unlock hidden realms.

Kuro belonged to the secretive Order of Silent Shadows, a group of elite ninjas tasked with protecting the balance between light and darkness. The Shinobu Rod was a symbol of their legacy, a tool passed down through generations to those deemed worthy of its formidable power.

However, the journey to harness the staff's true potential was not an easy one. The Silent Shadows had constructed a series of mystical platforms suspended in the air, each guarded by ancient spirits and challenges that tested a ninja's skill and precision. Kuro's master, the wise Sensei Hanzo, explained that only by mastering the art of timing could Kuro hope to overcome these obstacles and unlock the staff's hidden powers.

And so, Kuro embarked on a perilous journey through the floating platforms, a path fraught with danger and uncertainty. The Stick Ninja had to press and hold the spacebar to extend his staff, the Shinobu Rod, and time the extension precisely to leap from one platform to another. The challenge lay not only in the physical agility required but also in the mental discipline needed to navigate the ever-changing landscape of Shadowhaven.

As Kuro progressed, the staff revealed its secrets—allowing him to defy gravity, manipulate time, and even phase through barriers. Each successfully timed leap brought him closer to mastering the Shinobu Rod and unlocking its full potential.

The Stick Ninja's journey became a test not only of his physical prowess but also of his indomitable spirit. Only by persevering through the challenges of timing and precision could Kuro hope to unveil the true power of the Shinobu Rod and fulfill his destiny as the legendary Stick Ninja of Shadowhaven.


/////////////////////////////////////////////////////


The controls:-

1. The player needs to hold the "SHIFT" key to increase the height of the stick and release it when the player thinks the stick is sufficiently long.

2. While the ninja is crossing the bridge the player can press the "CONTROL" key to flip the player upside donw to collect the ORB OF LIFE.


/////////////////////////////////////////////////////


The Game mechanincs:-

1. There is a socring system in the game to keep track of the highscores and current score of the player.

2. The player can save their process anytime they feel fit,and can continue from their last save.

3. Every few Pillars or so an "ORB OF LIFE" appears bellow the stick path of the ninja, the player must flip the character and collect it and flip back up to prevent crashing into the wall.

4. If the player collects 3 orbs of life, then they have the option to revive and continue from where they fell. This ability uses up the orbs of life present with the player.


/////////////////////////////////////////////////////


Design pattern used:-

1. SINGLETON - The Singleton design pattern is a creational design pattern that ensures a class has only one instance and provides a global point to access that instance.

We have implemented this design pattern for the ninja class due to the fact only one ninja can be present at a time and duplicates of the ninja class are an unecessary problem.
This also has the benifit of acting as an global Ninja variable that can be used throughout the game.


2. ABSTRACT FACTORY - The Abstract Factory pattern is a creational design pattern that provides an interface for creating families of related or dependent objects without specifying their concrete classes.

We have implemented abstract factory in the cherry and ninja classes that get produced by respective factories and the corresponding factories are controlled by a factory controller.


/////////////////////////////////////////////////////


JUNIIT TEST

A testing class ghas been named under the name of "Testing".

It has 3 tests defined:-

1. Ninja_check: checks the initial values of ninja character.

2. Cherry_check: checks the initial values of cherry object.

3. Save_chech: checks if all the values in the save file are actually integers.


/////////////////////////////////////////////////////


HOW TO RUN THE GAME

the main class is defined and the code can be run from that in any SDK with appropriate instnace of javafx and java.(This piece of software has been written under openJDK21 & with help of Gluon Scene Builder)
GITHUB LINK - https://github.com/tarush537/project_AP_ver2

/////////////////////////////////////////////////////
