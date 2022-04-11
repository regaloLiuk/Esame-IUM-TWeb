import pygame
from pygame.locals import *
from SpriteSheet import *

file=open("score","r")
score=file.readline()
file.close()

todraw=pygame.sprite.Group()

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

    def update(enemies):
        collideWithEnemies(enemies)

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

        self.rect.y += self.move_y

        ycoll_player()
        screen.blit(self.image, (self.rect.x, self.rect.y))


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

    def collideWithEnemies(enemies):
        if pygame.sprite.spritecollide(player,enemies,False) and player.touch:
            player.touch=False
            player.life-=1

            if player.life==0:
                sys.stdout.write("\033[1;31m")
                print("Game Over!")
                sys.stdout.write("\033[0;0m")
                timer.shutdown()
                pygame.quit()
                exit()
