package com.example.morpionsolitaire.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid5D extends Grid {



    public Grid5D() throws IOException {
        this.load();
    }


    public List<Link> horizontalJoin(int line, int column){
        List<Link> possibleLinks = new ArrayList<>();
        int leftPivot = column - 1;
        int rightPivot = column + 1;
        int leftCounter = 0;
        int RightCounter = 0;
        while (leftPivot>=0 && leftCounter < 4){
            if (this.matrix[line][leftPivot].getValue()>0 &&
                !this.matrix[line][leftPivot].isLinked(LinkType.HORIZONTAL)){
                leftCounter++;
                leftPivot--;
            }else{
                break;
            }
        }
        while(rightPivot< WIDTH && RightCounter < 4){
            if (this.matrix[line][rightPivot].getValue()>0 &&
                !this.matrix[line][rightPivot].isLinked(LinkType.HORIZONTAL)){
                RightCounter++;
                rightPivot++;
            }else{
                break;
            }
        }
        leftPivot++;
        if (leftCounter==0 && RightCounter==4|| RightCounter==0 && leftCounter == 4){
            Cell[] items = new Cell[5];
            for(int i = leftPivot; i <= leftPivot + 4; i++){
                items[i-leftPivot] = this.matrix[line][i];
            }
            Link l = new Link(this.matrix[line][column], items, LinkType.HORIZONTAL);
            possibleLinks.add(l);
        } else if (leftCounter+RightCounter>=4){
            for(int k = leftPivot; k<leftPivot+leftCounter+RightCounter-3; k++){
                Cell[] items = new Cell[5];
                for(int i = k; i <= k + 4; i++){
                    items[i-k] = this.matrix[line][i];
                }
                possibleLinks.add(new Link(this.matrix[line][column], items, LinkType.HORIZONTAL));
            }
        }
        return possibleLinks;
    }


    public List<Link> canJoinVertically(int line, int column){ // rename to hasScoredVertical
        List<Link> possibleLinks = new ArrayList<>();
        int upPivot = line - 1;
        int downPivot = line + 1;
        int upCounter = 0;
        int downCounter = 0;
        while (upPivot>=0 && upCounter < 4){
            if (this.matrix[upPivot][column].getValue()>0 &&
                !this.matrix[upPivot][column].isLinked(LinkType.VERTICAL)){
                upCounter++;
                upPivot--;
            }else{
                break;
            }
        }
        while(downPivot< WIDTH && downCounter < 4){
            if (this.matrix[downPivot][column].getValue()>0 &&
                !this.matrix[downPivot][column].isLinked(LinkType.VERTICAL)){
                downCounter++;
                downPivot++;
            }else{
                break;
            }
        }
        upPivot++;
        if (upCounter==0 && downCounter==4|| downCounter==0 && upCounter==4){

            Cell[] items = new Cell[5];
            for(int i = upPivot; i <= upPivot + 4; i++){
                items[i-upPivot] = this.matrix[i][column];
            }
            Link l = new Link(this.matrix[line][column], items, LinkType.VERTICAL);
            possibleLinks.add(l);
        } else if (upCounter+downCounter>=4){
            for(int k = upPivot; k<upPivot+upCounter+downCounter-3; k++){
                Cell[] items = new Cell[5];
                for(int i = k; i <= k + 4; i++){
                    items[i-k] = this.matrix[i][column];
                }
                possibleLinks.add(new Link(this.matrix[line][column], items, LinkType.VERTICAL));
            }
        }
        return possibleLinks;
    }



    public List<Link> canJoinSecondDiagonal(int line, int column){
        List<Link> possibleLinks = new ArrayList<>();
        int downColumnPivot = column + 1;
        int downLinePivot = line + 1;
        int downCounter = 0;

        while(downLinePivot<HEIGHT && downColumnPivot<WIDTH && downCounter<4){
            if (this.matrix[downLinePivot][downColumnPivot].getValue()>0 &&
                !this.matrix[downLinePivot][downColumnPivot].isLinked(LinkType.SECOND_DIAGONAL)){
                downLinePivot++;
                downColumnPivot++;
                downCounter++;
            }
            else{
                break;
            }

        }

        int upLinePivot = line - 1;
        int upColumnPivot = column - 1;
        int upCounter = 0;
        while(upColumnPivot>0 && upLinePivot>0 && upCounter<4){
            if (this.matrix[upLinePivot][upColumnPivot].getValue()>0 &&
                !this.matrix[upLinePivot][upColumnPivot].isLinked(LinkType.SECOND_DIAGONAL)){
                upLinePivot--;
                upColumnPivot--;
                upCounter++;
            }
            else{
                break;
            }

        }
        upLinePivot++;
        upColumnPivot++;
        if (upCounter==0 && downCounter==4|| downCounter==0 && upCounter==4){
            Cell[] items = new Cell[5];
            int j = upColumnPivot;
            for(int i = upLinePivot; i <= upLinePivot + 4; i++){
                items[i-upLinePivot] = this.matrix[i][j];
                j++;
            }
            possibleLinks.add(new Link(this.matrix[line][column], items, LinkType.SECOND_DIAGONAL));

        }else if (upCounter+downCounter>=4) {
            int j = upColumnPivot;
            for (int k = upLinePivot; k < upLinePivot + upCounter + downCounter - 3; k++) {
                Cell[] items = new Cell[5];
                int p = j;
                for (int i = k; i <= k + 4; i++) {
                    items[i - k] = this.matrix[i][p];
                    p++;
                }
                j++;
                possibleLinks.add(new Link(this.matrix[line][column], items, LinkType.SECOND_DIAGONAL));
            }
        }
        return possibleLinks;
    }

    public List<Link> canJoinFirstDiagonal(int line, int column){
        List<Link> possibleLinks = new ArrayList<>();
        int downColumnPivot = column - 1;
        int downLinePivot = line + 1;
        int downCounter = 0;

        while(downLinePivot<HEIGHT && downColumnPivot>0 && downCounter<4){
            if (this.matrix[downLinePivot][downColumnPivot].getValue()>0 &&
                !this.matrix[downLinePivot][downColumnPivot].isLinked(LinkType.FIRST_DIAGONAL)){
                downLinePivot++;
                downColumnPivot--;
                downCounter++;
            }
            else{
                break;
            }
        }
        int upLinePivot = line - 1;
        int upColumnPivot = column + 1;
        int upCounter = 0;
        while(upColumnPivot<WIDTH && upLinePivot>0 && upCounter<4){
            if (this.matrix[upLinePivot][upColumnPivot].getValue()>0 &&
                !this.matrix[upLinePivot][upColumnPivot].isLinked(LinkType.FIRST_DIAGONAL)){
                upLinePivot--;
                upColumnPivot++;
                upCounter++;
            }
            else{
                break;
            }
        }
        upLinePivot++;
        upColumnPivot--;
        if (upCounter==0 && downCounter==4 || downCounter==0 && upCounter==4){
            Cell[] items = new Cell[5];
            int j = upColumnPivot;
            for(int i = upLinePivot; i <= upLinePivot + 4; i++){
                items[i-upLinePivot] = this.matrix[i][j];
                j--;
            }
            possibleLinks.add(new Link(this.matrix[line][column], items, LinkType.FIRST_DIAGONAL));
        }else if (upCounter+downCounter>=4) {
            int j = upColumnPivot;
            for (int k = upLinePivot; k < upLinePivot + upCounter + downCounter - 3; k++) {
                Cell[] items = new Cell[5];
                int p = j;
                for (int i = k; i <= k + 4; i++) {
                    items[i - k] = this.matrix[i][p];
                    p--;
                }
                j--;

                possibleLinks.add(new Link(this.matrix[line][column], items, LinkType.FIRST_DIAGONAL));
            }
        }
        return possibleLinks;
    }

    public List<Link> canLink(int line, int column){
        List<Link> links = new ArrayList<>();

        links.addAll(this.canJoinVertically(line, column));

        links.addAll(this.horizontalJoin(line, column));

        links.addAll(this.canJoinSecondDiagonal(line, column));

        links.addAll(this.canJoinFirstDiagonal(line, column));

        return links;
    }


    public void debug(){
        for(int i = 0; i < HEIGHT; i++){
            for (int j = 0; j<WIDTH;j++){
                System.out.print(matrix[i][j].isLinked(LinkType.HORIZONTAL)+ "    ");
            }
            System.out.println("  ");
        }
        System.out.println("------------------------------------------------------------");
    }
    public void resetCell(int i, int j){
        LinkType mainType = this.matrix[i][j].getMainLink();
        Cell[] link = this.matrix[i][j].getLinkedNodes().getNodes();
        for(Cell c : link){
            this.matrix[c.getI()][c.getJ()].unlink(mainType);
        }
        this.matrix[i][j].setMainLink(LinkType.NONE);
        this.matrix[i][j].unlink(mainType);
        this.matrix[i][j].setValue(0);
        //debug();
    }

    public void playMove(int i, int j) {
        List<Link> possiblities = canLink(i,j);
        if (possiblities.size() == 1 && this.matrix[i][j].getValue()==0){
            Link possibleLink = possiblities.get(0);
            setSingleLink(possibleLink);
        }
        else if(possiblities.size()>1){
            this.possibleMoves = possiblities;
            setSelectionCells();
        }
         if (this.matrix[i][j].canBeSelected()) {
            Link moveToPlay = getLinkFromSelectedcell(this.matrix[i][j]);
            removeSelection();
            setSingleLink(moveToPlay);
            this.possibleMoves.clear();
        }
    }

    public void playRandom(int i, int j){
        List<Link> possiblities = canLink(i,j);
        if (possiblities.size() >= 1 && this.matrix[i][j].getValue()==0){
            Random rand = new Random();
            int int_random = rand.nextInt(possiblities.size());
            Link possibleLink = possiblities.get(int_random);
            setSingleLink(possibleLink);
        }
    }

    /*public void removeSelection(){
        for(int i = 0; i < HEIGHT; i++){
            for (int j = 0; j<WIDTH;j++){
                this.matrix[i][j].setCanBeSelected(false);
            }
        }
    }*/

    /*public Link getLinkFromSelectedcell(Cell c){
        for(Link link: this.possibleMoves){
            if (c==link.getFirstNode() || c==link.getLastNode()){
                return link;
            }
        }
        return null;

    }*/
    /*
    public void setSelectionCells(){
        for(Link move: possibleMoves){
            if(move.getFirstNode() == move.getRoot()){
                move.getLastNode().setCanBeSelected(true);
            }else{
                move.getFirstNode().setCanBeSelected(true);
            }
        }
    }*/

    public void setSingleLink(Link l){
        int i = l.getRoot().getI();
        int j = l.getRoot().getJ();
        for (Cell c: l.getNodes()){
            c.link(l.getLinkType());
        }
        this.matrix[i][j].setLink(l);
        this.matrix[i][j].setValue(++scoreValue + 1);
        this.movesHistory.add(l);

    }

    public List<Link> getAllPossibleMoves(){
        List<Link> possibleMoves = new ArrayList<>();
        for (int i = 0; i < WIDTH; i++){
            for (int j = 0; j < HEIGHT; j++){
                possibleMoves.addAll(canLink(i,j));
            }
        }
        return possibleMoves;
    }

    public List<Link> getRandomSenario(){

        List<Link> link = new ArrayList<>();
        for(int i = 0; i<HEIGHT; i++){
            for (int j = 0; j<WIDTH; j++){
                List<Link> diagonal2 = this.canJoinSecondDiagonal(i,j);
                if (diagonal2.size()>0){
                    link.add(diagonal2.get(0));
                }
                List<Link> diagonal1 = this.canJoinFirstDiagonal(i,j);
                if (diagonal1.size()>0){
                    link.add(diagonal1.get(0));
                }
                List<Link> vertical = this.canJoinVertically(i,j);
                if (vertical.size()>0){
                    link.add(vertical.get(0));
                }
                List<Link> horizontal = this.horizontalJoin(i,j);
                if (horizontal.size()>0){
                    link.add(horizontal.get(0));
                }
            }
        }
        return link;
    }
}



