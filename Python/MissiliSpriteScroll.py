import pygame
from pygame.locals import *
import sys
import threading, time
import random
import constants

class SpriteSheet(object):
    """ Class used to grab images out of a sprite sheet. """
    # This points to our sprite sheet image


    def __init__(self, file_name):
        """ Constructor. Pass in the file name of the sprite sheet. """

        # Load the sprite sheet.
        self.sprite_sheet = pygame.image.load(file_name).convert()


    def get_image(self, x, y, width, height):
        """ Grab a single image out of a larger spritesheet
            Pass in the x, y location of the sprite
            and the width and height of the sprite. """

        # Create a new blank image
        image = pygame.Surface([width, height]).convert()

        # Copy the sprite from the large sheet onto the smaller image
        image.blit(self.sprite_sheet, (0, 0), (x, y, width, height))

        # Assuming black works as the transparent color
        image.set_colorkey(constants.BLACK)

        # Return the image
        return image


# Game Initialization
pygame.init()

# Center the Game Application

# Game Resolution
screen_width=550
screen_height=240
screen=pygame.display.set_mode((screen_width, screen_height))

# Text Renderer
def text_format(message, textFont, textSize, textColor):
    newFont=pygame.font.Font(textFont, textSize)
    newText=newFont.render(message, 0, textColor)
    return newText


# Colors
white=(255, 255, 255)
black=(0, 0, 0)
gray=(50, 50, 50)
red=(255, 0, 0)
green=(0, 255, 0)
blue=(0, 0, 255)
yellow=(255, 255, 0)

# Game Fonts
font = "Retro.ttf"


# Game Framerate
clock = pygame.time.Clock()
FPS=30


class Background(pygame.sprite.Sprite):
    def __init__(self, image_file, location):
        pygame.sprite.Sprite.__init__(self)  #call Sprite initializer
        self.image = pygame.image.load(image_file)
        self.rect = self.image.get_rect()
        self.rect.left, self.rect.top = location
# Main Menu

BackGround = Background('sfondo_menu_erba.png', [0,0])

def main_menu():

    menu=True
    selected="start"

    while menu:
        for event in pygame.event.get():
            if event.type==pygame.QUIT:
                pygame.quit()
                quit()
            if event.type==pygame.KEYDOWN:
                if event.key==pygame.K_UP:
                    selected="start"
                elif event.key==pygame.K_DOWN:
                    selected="quit"
                if event.key==pygame.K_RETURN:
                    if selected=="start":
                        print("Start")
                        menu=False
                    if selected=="quit":
                        timer.shutdown()
                        pygame.quit()
                        quit()

        # Main Menu UI
        #pygame.image.load("sfondo_menu.png").convert()
        title=text_format("Dino Adventure", font, 50, yellow)
        if selected=="start":
            text_start=text_format("START", font, 25, white)
        else:
            text_start = text_format("START", font, 25, black)
        if selected=="quit":
            text_quit=text_format("QUIT", font, 25, white)
        else:
            text_quit = text_format("QUIT", font, 25, black)

        title_rect=title.get_rect()
        start_rect=text_start.get_rect()
        quit_rect=text_quit.get_rect()

        # Main Menu Text
        screen.blit(BackGround.image, BackGround.rect)
        screen.blit(title, (screen_width/2 - (title_rect[2]/2), 10))
        screen.blit(text_start, (screen_width/2 - (start_rect[2]/2), 75))
        screen.blit(text_quit, (screen_width/2 - (quit_rect[2]/2), 100))

        pygame.display.update()
        clock.tick(FPS)
        #pygame.display.set_caption("Python - Pygame Simple Main Menu Selection")

#Initialize the Game





class Timer(threading.Thread):
    count=0
    def __init__(self):
        threading.Thread.__init__(self)
        self.stopFlag = 0

    def shutdown(self):
        self.stopFlag = 1

    def run(self):
        self.stopFlag = 0
        while not self.stopFlag:
            time.sleep(1.5)
            player.touch=True
            if pause==False:
                self.count+=1.5


#from pygame_function import *
#Inizializziamo Pygame, schermo e clock

showMenu=True

pygame.init()
screen=pygame.display.set_mode((screen_width,screen_height),0,32)
pygame.display.set_caption("Gioco")
clock=pygame.time.Clock()
#background_image = pygame.image.load("sfondo.png").convert()

"""Creiamo i 2 principali gruppi di sprites: le sprites da disegnare in generale
e le piattaforme, in un gruppo separato per poter usare le funzioni di collisione"""

#fnt = pygame.font.SysFont("Times New Roman", 20)
#surf_text = fnt.render("Hai vinto", True, (225, 255, 0))

todraw=pygame.sprite.Group()
plats=pygame.sprite.Group()
enemies=pygame.sprite.Group()
exit=pygame.sprite.Group()

file = 'ost.wav'
hit= "CHA2.wav"
pygame.mixer.init()
pygame.mixer.music.load(file)
#pygame.mixer.music.load(file)
pygame.mixer.music.play(-1)


class Enemy(pygame.sprite.Sprite):
    move_x=10
    def __init__(self,x,y,c):

        pygame.sprite.Sprite.__init__(self)
        self.image=pygame.image.load("missile_preciso.png")
        self.rect=self.image.get_rect()
        self.rect.x=570
        self.rect.y=random.randint(10, 210)
        enemies.add(self)

    def update(self):
        self.rect.x-=self.move_x

    #move_x=0

    #def __init__(self,x,y,c):
    #    pygame.sprite.Sprite.__init__(self)
    #    self.image=pygame.image.load("hero_dx.png")
    #    self.rect=self.image.get_rect()
    #    self.rect.x=x
    #    self.rect.y=y
    #    enemies.add(self)

    #def update(self):
    #    self.rect.x+=self.move_x


#Classe per la creazione delle piattaforme
class Platform(pygame.sprite.Sprite):
    move_x=0
    move_y=0

    def __init__(self,x,y,c):
        if c=='#':
            pygame.sprite.Sprite.__init__(self)
            #self.image=pygame.Surface((20,20))
            self.image=pygame.image.load("wall.png")
            #self.image.fill((255,0,0))
            self.rect=self.image.get_rect()
            self.rect.x=x
            self.rect.y=y
            plats.add(self)
        elif c=='E':
            pygame.sprite.Sprite.__init__(self)
            #self.image=pygame.Surface((20,20))
            #self.image.fill((0,255,0))
            self.image=pygame.image.load("door.png")
            self.rect=self.image.get_rect()
            self.rect.x=x
            self.rect.y=y
            #plats.add(self)
            exit.add(self)

        #elif c=='S':
        #    Enemy(x,y,c)
            #pygame.sprite.Sprite.__init__(self)
            #self.image=pygame.image.load("hero_dx.png")
            #self.rect=self.image.get_rect()
            #self.rect.x=x
            #self.rect.y=y
            #enemies.add(self)



    def update(self):
        self.rect.x+=self.move_x
        #xcoll(self)
        self.rect.y+=self.move_y
        #if enemies.has(self):
        #    screen.blit(self.image, (self.rect.x, self.rect.y))


""" Le collisioni vengono calcolate separatamente sull'asse x ed y
altrimenti il nostro personaggio sembrerà teletrasportarsi"""

def xcoll():
    collision = pygame.sprite.spritecollide(player,plats,False)
    if collision:
        for block in plats:
            block.rect.x-=block.move_x
            block.move_x=0
            screen.blit(block.image, (block.rect.x, block.rect.y))

        for block in enemies:
            block.rect.x-=block.move_x
            #block.move_x=0
            screen.blit(block.image, (block.rect.x, block.rect.y))

    else:
        for block in plats:
            screen.blit(block.image, (block.rect.x, block.rect.y))

        for block in enemies:
            screen.blit(block.image, (block.rect.x, block.rect.y))


#Classe giocatore
class Player(pygame.sprite.Sprite):
    life=3
    move_x=0
    move_y=0
    touch=True
    onground=False

    direction="X"
    # This holds all the images for the animated walk left/right
    # of our player
    walking_frames_l = []
    walking_frames_r = []

    # What direction is the player facing?
    # -- Methods
    def __init__(self):
        """ Constructor function """

        # Call the parent's constructor
        pygame.sprite.Sprite.__init__(self)
        self.pos=0

        sprite_sheet = SpriteSheet("dino_giusto_dx.png")





        image = sprite_sheet.get_image(120, 0, 19, 19)
        self.walking_frames_r.append(image)
        image = sprite_sheet.get_image(144, 0, 19, 19)
        self.walking_frames_r.append(image)
        image = sprite_sheet.get_image(168, 0, 19, 19)
        self.walking_frames_r.append(image)
        image = sprite_sheet.get_image(192, 0, 19, 19)
        self.walking_frames_r.append(image)
        image = sprite_sheet.get_image(216, 0, 19, 19)
        self.walking_frames_r.append(image)
        image = sprite_sheet.get_image(240, 0, 19, 19)
        self.walking_frames_r.append(image)

        sprite_sheet = SpriteSheet("dino_giusto_sx.png")

        image = sprite_sheet.get_image(479, 0, 19, 19)
        self.walking_frames_l.append(image)
        image = sprite_sheet.get_image(455, 0, 19, 19)
        self.walking_frames_l.append(image)
        image = sprite_sheet.get_image(431, 0, 19, 19)
        self.walking_frames_l.append(image)
        image = sprite_sheet.get_image(407, 0, 19, 19)
        self.walking_frames_l.append(image)
        image = sprite_sheet.get_image(383, 0, 19, 19)
        self.walking_frames_l.append(image)





            # Set the image the player starts with
        self.image = self.walking_frames_r[self.pos]

            # Set a referance to the image rect.
        self.rect = self.image.get_rect()
        self.rect.x=250
        self.rect.y=100
        todraw.add(self)

    def update(self):
        collideWithExit()

        #self.rect.x += self.move_x

        if self.direction == "R":
            self.pos+=1
            frame = (self.pos//2) % len(self.walking_frames_r)
            self.image = self.walking_frames_r[frame]

        elif self.direction=="L":
            self.pos+=1
            frame = (self.pos//2) % len(self.walking_frames_l)
            self.image = self.walking_frames_l[frame]

        else:
            if self.direction=="L":
                frame = (self.pos//2) % len(self.walking_frames_l)
                self.image = self.walking_frames_l[self.pos]
            elif self.direction=="R":
                frame = (self.pos//2) % len(self.walking_frames_r)
                self.image = self.walking_frames_l[self.pos]
        # Move up/down
        self.rect.y += self.move_y

        ycoll_player()
        screen.blit(self.image, (self.rect.x, self.rect.y))

    def go_left(self):
        """ Called when the user hits the left arrow. """
        self.direction = "L"

    def go_right(self):
        """ Called when the user hits the right arrow. """
        self.direction = "R"



class Life(pygame.sprite.Sprite):
    def __init__(self,x,y):
        pygame.sprite.Sprite.__init__(self)
        self.image=pygame.image.load("heart.png")
        self.rect=self.image.get_rect()
        self.rect.x=x
        self.rect.y=y

def updateHeart():
    if player.life==3:
        heart={Life(10,10),Life(30,10),Life(50,10)}
    elif player.life==2:
        heart={Life(10,10),Life(30,10)}
    elif player.life==1:
        heart={Life(10,10)}

    for i in heart:
        screen.blit(i.image, (i.rect.x, i.rect.y))


def xcoll_player():
    collision=pygame.sprite.spritecollide(player, plats, False)
    for block in collision:
        if player.move_x>0:
            player.rect.right=block.rect.left
        if player.move_x<0:
            player.rect.left=block.rect.right

def ycoll_player():
        collision=pygame.sprite.spritecollide(player, plats, False)
        player.onground=False
        for block in collision:
            if player.move_y==0:
                player.onground=True
            if player.move_y<0:
                player.rect.top=block.rect.bottom
                player.move_y=0     #serve per evitare l'impressione che il giocatore si "appiccichi" al soffitto
                player.onground=False
            if player.move_y>0:
                player.rect.bottom=block.rect.top
                player.onground=True


def collideWithExit():
    if pygame.sprite.spritecollide(player,enemies,False) and player.touch:
        player.touch=False
        player.life-=1
        #pygame.mixer.music.play(1)

        if player.life==0:
            sys.stdout.write("\033[1;31m")
            print("Sei resistito",timer.count,"secondi")
            sys.stdout.write("\033[0;0m")
            timer.shutdown()
            exit()



"""Una semplice routine per costruire il livello, ogni "#" corrisponde
ad un blocco 20x20 pixel di terreno/piattaforma"""
def build():
    myx=0
    myy=0
    level=[
            '                           ',
            '                           ',
            '                           ',
            '                           ',
            '                           ',
            '                           ',
            '#      ######         #####',
            '#            #     #      #',
            '#                ####     #  #      #  ',
            '#              #   #  #   #        ##  ',
            '#                  #      #      ##### ',
            '#######################################']
    for r in level:
        for c in r:
            if c==' ':
                pass
            elif c=='#':
                p=Platform(myx,myy,c)
            elif c=='E':
                p=Platform(myx,myy,c)
            elif c=='S':
                p=Enemy(myx,myy,c)
            myx+=20
        myy+=20
        myx=0
#Simulazione di gravità, molto semplicistica
def gravity():
    if not player.onground:
        player.move_y+=0.9

#def scroll(x,y):
#    for i in plats:
#        i.rect.x+=x
#        i.rect.y+=y

main_menu()
player=Player()
timer=Timer()
timer.start()
build()
pause=False
BackGround = Background('sfondo_menu_erba.png', [0,0])
#Ciclo di gioco


while True:
    screen.fill((0,0,0))
    gravity()
    #Ciclo eventi
    for event in pygame.event.get():
        if event.type==QUIT:  #Uscita
            exit()
        if event.type==KEYDOWN: #Viene premuto un tasto
            if event.key==K_UP:   #Su
                if player.onground:   #Salta solo se il giocatore è a terra
                    player.move_y=-9
                player.onground=False
            if event.key==K_LEFT:   #Sinistra
                #player.move_x=-5
                #BackGround.image.scroll(-1,0)
                #player.image=pygame.image.load("luigi_sx.png")
                player.direction="L"
                for i in plats:
                    i.move_x=+5

                #for i in enemies:
                #    i.move_x=+5;

            if event.key==K_RIGHT:   #Destra
                #player.move_x=5
                #BackGround.image.scroll(1,0)

                #player.image=pygame.image.load("luigi_dx.png")
                player.direction="R"
                for i in plats:
                    i.move_x=-5


                #for i in enemies:
                #    i.move_x=-5;


            if event.key==K_ESCAPE: #Esco dal gioco
                main_menu()
                #timer.shutdown()
                #exit()

            #if event.key==K_DOWN:   #Destra
            #    player.move_y=+5
        if event.type==KEYUP:  #Viene rilasciato un tasto
            if event.key==K_LEFT:
                #player.move_x=0
                for i in plats:
                    i.move_x=0

                player.direction="X"
            #    for i in enemies:
            #        i.move_x=0;

            if event.key==K_RIGHT:
                #player.move_x=0
                for i in plats:
                    i.move_x=0


                player.direction="X"
                #for i in enemies:
                #    i.move_x=0;

            #if event.key==K_UP:
            #    player.move_y=0
            #if event.key==K_DOWN:
            #    player.move_y=0

    if random.randint(1,20)<=1:
        Enemy(3,4,4)

    #Aggiorna tutte le sprites e lo schermo

    screen.blit(BackGround.image, BackGround.rect)

    player.update()
    plats.update()
    enemies.update()
    xcoll();
    updateHeart()
    exit.update()
    pygame.display.update()
    #Faccio in modo che il gioco non vada oltre i 30FPS
    clock.tick(30)
