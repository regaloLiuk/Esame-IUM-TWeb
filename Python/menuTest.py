import pygame
from pygame.locals import *

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
                    if selected=="quit":
                        pygame.quit()
                        quit()

        # Main Menu UI
        #pygame.image.load("sfondo_menu.png").convert()
        title=text_format("Hero Adventure", font, 50, yellow)
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
main_menu()
pygame.quit()
quit()
