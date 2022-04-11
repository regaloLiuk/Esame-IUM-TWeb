import pygame
from pygame.locals import *
import sys
import threading, time
import random


red=(255, 0, 0)
white=(255, 255, 255)
blue=(0, 0, 255)
green=(0, 100, 100)
black=(0, 0, 0)
yellow=(255, 255, 0)
font = "Retro.ttf"
clock = pygame.time.Clock()
FPS=30


class SpriteSheet(object):

    sprite_sheet = None

    def __init__(self, file_name):
        self.sprite_sheet = pygame.image.load(file_name).convert()


    def get_image(self, x, y, width, height):
        image = pygame.Surface([width, height]).convert()
        image.blit(self.sprite_sheet, (0, 0), (x, y, width, height))
        image.set_colorkey(black)
        return image


pygame.init()


screen_width=550
screen_height=240
screen=pygame.display.set_mode((screen_width, screen_height))


def text_format(message, textFont, textSize, textColor):
    newFont=pygame.font.Font(textFont, textSize)
    newText=newFont.render(message, 0, textColor)
    return newText



file=open("score","r")
score=file.readline()

class Background(pygame.sprite.Sprite):

    def __init__(self, image_file, location):
        self.ifScroll=False
        self.move="right"#modifica
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.image.load(image_file)
        self.rect = self.image.get_rect()
        self.rect.left, self.rect.top = location


#backGround = Background('sfondoLungo.png', [-100,0])
backGround = Background('sfondoPrato.png', [-100,0])


def main_menu(begin):

    menu=True
    selected="start"


    while menu:
        for event in pygame.event.get():
            if event.type==pygame.QUIT:
                timer.shutdown()
                pygame.quit()
                exit()
            elif event.type==pygame.KEYDOWN:
                if event.key==pygame.K_UP:
                    selected="start"
                elif event.key==pygame.K_DOWN:
                    selected="quit"
                if event.key==pygame.K_RETURN:
                    if selected=="start":
                        menu=False
                    elif selected=="quit":
                        if begin==False:
                            timer.shutdown()
                        pygame.quit()
                        quit()

        score_txt=title=text_format("Best score: "+score, font, 25, blue)
        title=text_format("Dino Adventure", font, 50, yellow)
        if selected=="start":
            text_start=text_format("->START<-", font, 25, green)
        else:
            text_start = text_format("START", font, 25, red)
        if selected=="quit":
            text_quit=text_format("->QUIT<-", font, 25, green)
        else:
            text_quit = text_format("QUIT", font, 25, red)

        score_rect=score_txt.get_rect()
        title_rect=title.get_rect()
        start_rect=text_start.get_rect()
        quit_rect=text_quit.get_rect()


        screen.blit(backGround.image, backGround.rect)
        screen.blit(score_txt, (screen_width/2 + (140), 10))
        screen.blit(title, (screen_width/2 - (title_rect[2]/2), 10))
        screen.blit(text_start, (screen_width/2 - (start_rect[2]/2), 75))
        screen.blit(text_quit, (screen_width/2 - (quit_rect[2]/2), 100))


        pygame.display.update()
        clock.tick(FPS)



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
            self.count+=1.5



showMenu=True

pygame.init()
screen=pygame.display.set_mode((550,240),0,32)
pygame.display.set_caption("Dino Adventure")
clock=pygame.time.Clock()

todraw=pygame.sprite.Group()
plats=pygame.sprite.Group()
enemies=pygame.sprite.Group()
finish=pygame.sprite.Group()

sound_ost = "ost.wav"
sound_hit = pygame.mixer.Sound("hit.wav")

pygame.mixer.init()
pygame.mixer.music.load(sound_ost)
pygame.mixer.music.play(-1)




class Enemy(pygame.sprite.Sprite):

    def __init__(self):
        self.move_x=10
        pygame.sprite.Sprite.__init__(self)
        self.image=pygame.image.load("missileChiaroPreciso.png")
        self.rect=self.image.get_rect()
        self.rect.x=570
        self.rect.y=random.randint(10, 210)
        enemies.add(self)

    def update(self):
        self.rect.x-=self.move_x

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

        elif c=='S':
            pygame.sprite.Sprite.__init__(self)
            self.image=pygame.image.load("door.png")
            self.rect=self.image.get_rect()
            self.rect.x=x
            self.rect.y=y
            finish.add(self)
            plats.add(self)


    def update(self):
        self.rect.x+=self.move_x
        self.rect.y+=self.move_y



def xcoll():
    collideWithExit()
    collision = pygame.sprite.spritecollide(player,plats,False)
    if collision:
        for block in plats:
            block.rect.x-=block.move_x
            block.move_x=0
            screen.blit(block.image, (block.rect.x, block.rect.y))

        for block in enemies:
            screen.blit(block.image, (block.rect.x, block.rect.y))
        backGround.ifScroll=False

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
    tmp_score=float(score)
    direction="X"

    walking_frames_l = []
    walking_frames_r = []



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


        self.image = self.walking_frames_r[self.pos]

        self.rect = self.image.get_rect()
        self.rect.x=250
        self.rect.y=100
        todraw.add(self)

    def update(self):
        collideWithEnemies()

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


def ycoll_player():
        if player.rect.y>260:
            sys.stdout.write("\033[1;31m")
            print("Game Over!")
            sys.stdout.write("\033[0;0m")
            timer.shutdown()
            pygame.quit()
            exit()
        collision=pygame.sprite.spritecollide(player, plats, False)
        player.onground=False
        for block in collision:
            if player.move_y==0:
                player.onground=True
            elif player.move_y<0:
                player.rect.top=block.rect.bottom
                player.move_y=0     #serve per evitare l'impressione che il giocatore si "appiccichi" al soffitto
                player.onground=False
            elif player.move_y>0:
                player.rect.bottom=block.rect.top
                player.onground=True


def collideWithEnemies():
    if pygame.sprite.spritecollide(player,enemies,True) and player.touch:
        sound_hit.play()
        player.touch=False
        player.life-=1

        if player.life==0:
            sys.stdout.write("\033[1;31m")
            print("Game Over!")
            sys.stdout.write("\033[0;0m")
            timer.shutdown()
            pygame.quit()
            exit()

def collideWithExit():
    if pygame.sprite.spritecollide(player,finish,False):
        if float(timer.count)<float(player.tmp_score):
            #print(str(timer.count)+" "+str(player.tmp_score))
            file=open("score","w")
            file.write(str(timer.count))
            file.flush()
            file.close()
        timer.shutdown()
        pygame.quit()
        exit()


def build():
    myx=0
    myy=0
    level=[
            '                           ',
            '                           ',
            '                           ',
            '                           ',
            '                                                                                                ',
            '                                                                                        ###     ',
            '                                                                                      ###       ',
            '                                                                                                ',
            '#                                                               #  ####   ##   ##   #           ',
            '#                         #                                    ###                              ',
            '#                        ##          #              #         #####                           S',
            '################# #####  #############  ##########  #####################       ##      ####### ']
    for r in level:
        for c in r:
            if c==' ':
                pass
            elif c=='#':
                p=Platform(myx,myy,c)
            elif c=='E':
                p=Platform(myx,myy,c)
            elif c=='S':
                p=Platform(myx,myy,c)
            myx+=20
        myy+=20
        myx=0
def gravity():
    if not player.onground:
        player.move_y+=0.9


main_menu(True)
player=Player()
timer=Timer()
timer.start()
build()

#BackGround = Background('sfondoLungo.png', [0,0])


while True:
    screen.fill((0,0,0))
    gravity()

    for event in pygame.event.get():
        if event.type==QUIT:
            timer.shutdown()
            pygame.quit()
            exit()
        elif event.type==KEYDOWN:
            if event.key==K_UP:
                if player.onground:
                    player.move_y=-9
                player.onground=False

            elif event.key==K_LEFT:
                player.direction="L"
                for i in plats:
                    i.move_x=+5
                backGround.move="left"
                backGround.ifScroll=True


            elif event.key==K_RIGHT:
                player.direction="R"
                for i in plats:
                    i.move_x=-5
                backGround.move="right"
                backGround.ifScroll=True

            elif event.key==K_ESCAPE:
                main_menu(False)

        elif event.type==KEYUP:
            if event.key==K_LEFT:
                for i in plats:
                    i.move_x=0
                player.direction="X"
                backGround.ifScroll=False

            elif event.key==K_RIGHT:
                for i in plats:
                    i.move_x=0
                player.direction="X"
                backGround.ifScroll=False



    if random.randint(1,20)<=2:
        Enemy()


    screen.blit(backGround.image, backGround.rect)

    player.update()
    plats.update()
    enemies.update()
    xcoll();
    updateHeart()


    if(backGround.ifScroll==True):
        if(backGround.move=="left"):
            backGround.image.scroll(1,0)
        elif(backGround.move=="right"):
            backGround.image.scroll(-2,0)




    pygame.display.update()
    clock.tick(FPS)
