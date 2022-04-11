import pygame
from pygame.locals import *
import sys
import threading, time
import random
from Player import *


white=(255, 255, 255)
black=(0, 0, 0)
yellow=(255, 255, 0)
font = "Retro.ttf"


file=open("score","r")
score=file.readline()
file.close()



pygame.init()


screen_width=550
screen_height=240
screen=pygame.display.set_mode((screen_width, screen_height))


def text_format(message, textFont, textSize, textColor):
    newFont=pygame.font.Font(textFont, textSize)
    newText=newFont.render(message, 0, textColor)
    return newText


clock = pygame.time.Clock()
FPS=30



class Background(pygame.sprite.Sprite):
    def __init__(self, image_file, location):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.image.load(image_file)
        self.rect = self.image.get_rect()
        self.rect.left, self.rect.top = location


BackGround = Background('sfondo_menu_erba.png', [0,0])

def main_menu(begin):

    menu=True
    selected="start"

    """file=open("score","r")
    score=file.readline()
    print(score)
    tmp_score=float(score)"""

    while menu:
        for event in pygame.event.get():
            if event.type==pygame.QUIT:
                pygame.quit()
                quit()
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

        score_txt=title=text_format("Best score: "+score, font, 25, white)
        title=text_format("Dino Adventure", font, 50, yellow)
        if selected=="start":
            text_start=text_format("START", font, 25, white)
        else:
            text_start = text_format("START", font, 25, black)
        if selected=="quit":
            text_quit=text_format("QUIT", font, 25, white)
        else:
            text_quit = text_format("QUIT", font, 25, black)

        score_rect=score_txt.get_rect()
        title_rect=title.get_rect()
        start_rect=text_start.get_rect()
        quit_rect=text_quit.get_rect()


        screen.blit(BackGround.image, BackGround.rect)
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

#todraw=pygame.sprite.Group()
plats=pygame.sprite.Group()
enemies=pygame.sprite.Group()
finish=pygame.sprite.Group()

file = 'ost.wav'
pygame.mixer.init()
pygame.mixer.music.load(file)
pygame.mixer.music.play(-1)


class Enemy(pygame.sprite.Sprite):

    def __init__(self,x,y,c):
        self.move_x=10
        pygame.sprite.Sprite.__init__(self)
        self.image=pygame.image.load("missile_preciso.png")
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

    else:
        for block in plats:
            screen.blit(block.image, (block.rect.x, block.rect.y))

        for block in enemies:
            screen.blit(block.image, (block.rect.x, block.rect.y))


#Classe giocatore



def collideWithExit():
    if pygame.sprite.spritecollide(player,finish,False):
        if float(timer.count)<float(player.tmp_score):
            print(str(timer.count)+" "+str(player.tmp_score))
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
            '                           ',
            '                           ',
            '                                                                            S',
            '                                                                            S',
            '                                                                            S',
            '                                                                            ',
            '    S                                                                       S',
            '############################################################################']
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

BackGround = Background('sfondo_menu_erba.png', [0,0])


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

            elif event.key==K_RIGHT:
                player.direction="R"
                for i in plats:
                    i.move_x=-5

            elif event.key==K_ESCAPE:
                main_menu(False)

        elif event.type==KEYUP:
            if event.key==K_LEFT:
                for i in plats:
                    i.move_x=0
                player.direction="X"

            elif event.key==K_RIGHT:
                for i in plats:
                    i.move_x=0
                player.direction="X"

    if random.randint(1,20)<=1:
        Enemy(3,4,4)


    screen.blit(BackGround.image, BackGround.rect)

    player.update()
    plats.update()
    enemies.update()
    xcoll();
    updateHeart()

    pygame.display.update()
    clock.tick(30)
