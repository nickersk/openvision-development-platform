#!/bin/sh
echo ""
RED='\033[0;31m'
NC='\033[0m' # No Color
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[0;33m'
echo "This is only for Open Vision developers with push access"
echo ""
echo -e "First choose what kind of submodule update do you want?"
echo -e "Answers are in ${GREEN}green:${NC}"
echo -e "${GREEN}All ${NC}- ${GREEN}Specific ${NC}- ${RED}BitBake ${NC}- ${RED}OpenEmbedded ${NC}- ${RED}Core ${NC}- ${NC}- ${RED}QT5"
echo -e ""
echo -e "${NC}Enter submodule type:${GREEN}"
echo -e ""
read SUBMODULETYPE
echo -e "${NC}"
if [ $SUBMODULETYPE != "All" -a $SUBMODULETYPE != "Specific" -a $SUBMODULETYPE != "BitBake" -a $SUBMODULETYPE != "OpenEmbedded" -a $SUBMODULETYPE != "Core" -a $SUBMODULETYPE != "QT5" ]
then
	echo -e "${RED}Not a valid answer!${NC}"
	echo -e ""
	exit 0
fi
echo "Stage 1: git pull for new changes"
echo ""
if [ $SUBMODULETYPE = "Specific" ]
then
	echo -e "${NC}Specific mode does not support BitBake, OpenEmbedded, Core and QT5"
	echo -e ""
	echo -e "Enter submodule name without meta-:${GREEN}"
	echo -e ""
	read SUBMODULENAME
	echo -e "${NC}"
	cd meta-${SUBMODULENAME}
	echo "Checking out meta-${SUBMODULENAME} develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	echo "Stage 2: git add for new changes"
	echo ""
	git add meta-${SUBMODULENAME}
	git commit --dry-run
	read -p "The above changes will be committed and pushed to Open Vision, [A]bort [P]roceed: " choice
	if [ "$choice" = "P" -o "$choice" = "p" ];then
		git commit -S -m "Update meta-${SUBMODULENAME} submodule using submodule.sh"
		echo "Stage 3: git push for new changes"
		echo ""
		git push
		echo "Done: the repository got updated to its latest version!"
		echo ""
	else 
		exit 0
	fi
fi
if [ $SUBMODULETYPE = "BitBake" ]
then
	cd bitbake
	echo "Checking out bitbake master branch:"
	git checkout master
	git pull
	echo -e "\n"
	cd ..
	echo "Stage 2: git add for new changes"
	echo ""
	git add bitbake
	git commit --dry-run
	read -p "This is serious, if you don't know what is this or you're not 100% sure about it just [A]bort otherwise the above changes will be committed and pushed to Open Vision and the result could be catastrophic, [P]roceed? " choice
	if [ "$choice" = "P" -o "$choice" = "p" ];then
		git commit -S -m "Update bitbake submodule using submodule.sh"
		echo "Stage 3: git push for new changes"
		echo ""
		git push
		echo "Done: the repository got updated to its latest version!"
		echo ""
	else 
		exit 0
	fi
fi
if [ $SUBMODULETYPE = "OpenEmbedded" ]
then
	cd meta-openembedded
	echo "Checking out meta-openembedded master branch:"
	git checkout master
	git pull
	echo -e "\n"
	cd ..
	echo "Stage 2: git add for new changes"
	echo ""
	git add meta-openembedded
	git commit --dry-run
	read -p "This is serious, if you don't know what is this or you're not 100% sure about it just [A]bort otherwise the above changes will be committed and pushed to Open Vision and the result could be catastrophic, [P]roceed? " choice
	if [ "$choice" = "P" -o "$choice" = "p" ];then
		git commit -S -m "Update meta-openembedded submodule using submodule.sh"
		echo "Stage 3: git push for new changes"
		echo ""
		git push
		echo "Done: the repository got updated to its latest version!"
		echo ""
	else 
		exit 0
	fi
fi
if [ $SUBMODULETYPE = "Core" ]
then
	cd openembedded-core
	echo "Checking out openembedded-core master branch:"
	git checkout master
	git pull
	echo -e "\n"
	cd ..
	echo "Stage 2: git add for new changes"
	echo ""
	git add openembedded-core
	git commit --dry-run
	read -p "This is serious, if you don't know what is this or you're not 100% sure about it just [A]bort otherwise the above changes will be committed and pushed to Open Vision and the result could be catastrophic, [P]roceed? " choice
	if [ "$choice" = "P" -o "$choice" = "p" ];then
		git commit -S -m "Update openembedded-core submodule using submodule.sh"
		echo "Stage 3: git push for new changes"
		echo ""
		git push
		echo "Done: the repository got updated to its latest version!"
		echo ""
	else 
		exit 0
	fi
fi
if [ $SUBMODULETYPE = "QT5" ]
then
	cd meta-qt5
	echo "Checking out meta-qt5 master branch:"
	git checkout master
	git pull
	echo -e "\n"
	cd ..
	echo "Stage 2: git add for new changes"
	echo ""
	git add meta-qt5
	git commit --dry-run
	read -p "This is serious, if you don't know what is this or you're not 100% sure about it just [A]bort otherwise the above changes will be committed and pushed to Open Vision and the result could be catastrophic, [P]roceed? " choice
	if [ "$choice" = "P" -o "$choice" = "p" ];then
		git commit -S -m "Update meta-qt5 submodule using submodule.sh"
		echo "Stage 3: git push for new changes"
		echo ""
		git push
		echo "Done: the repository got updated to its latest version!"
		echo ""
	else 
		exit 0
	fi
fi
if [ $SUBMODULETYPE = "All" ]
then
	echo -e "All mode does not support BitBake, OpenEmbedded, Core, Python2 and QT5"
	echo -e ""
	cd meta-amiko
	echo "Checking out meta-amiko develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-ax
	echo "Checking out meta-ax develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-azbox
	echo "Checking out meta-azbox develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-beyonwiz
	echo "Checking out meta-beyonwiz develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-blackbox
	echo "Checking out meta-blackbox develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-broadmedia
	echo "Checking out meta-broadmedia develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-ceryon
	echo "Checking out meta-ceryon develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-dags
	echo "Checking out meta-dags develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-dinobot
	echo "Checking out meta-dinobot develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-dreambox
	echo "Checking out meta-dreambox develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-ebox
	echo "Checking out meta-ebox develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-edision
	echo "Checking out meta-edision develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-entwopia
	echo "Checking out meta-entwopia develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-formuler
	echo "Checking out meta-formuler develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-gfutures
	echo "Checking out meta-gfutures develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-gigablue
	echo "Checking out meta-gigablue develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-ini
	echo "Checking out meta-ini develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-ixuss
	echo "Checking out meta-ixuss develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-maxytec
	echo "Checking out meta-maxytec develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-octagon
	echo "Checking out meta-octagon develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-odin
	echo "Checking out meta-odin develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-protek
	echo "Checking out meta-protek develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-sh4
	echo "Checking out meta-sh4 develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-tiviar
	echo "Checking out meta-tiviar develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-tripledot
	echo "Checking out meta-tripledot develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-uclan
	echo "Checking out meta-uclan develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-ultramini
	echo "Checking out meta-ultramini develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-vuplus
	echo "Checking out meta-vuplus develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-xcore
	echo "Checking out meta-xcore develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-xp
	echo "Checking out meta-xp develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-xtrend
	echo "Checking out meta-xtrend develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	cd meta-zgemma
	echo "Checking out meta-zgemma develop branch:"
	git checkout develop
	git pull
	echo -e "\n"
	cd ..
	echo "Stage 2: git add for new changes"
	echo ""
	git add meta-*
	git commit --dry-run
	read -p "The above changes will be committed and pushed to Open Vision, [A]bort [P]roceed: " choice
	if [ "$choice" = "P" -o "$choice" = "p" ];then
		git commit -S -m "Update submodules using submodule.sh"
		echo "Stage 3: git push for new changes"
		echo ""
		git push
		echo "Done: all git repositories got updated to their latest versions!"
		echo ""
	else 
		exit 0
	fi
fi
