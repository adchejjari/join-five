/**
 * GameController is a controller class that handles the interactions between the game's model (grid) and view (GameBoardView).
 * It listens to events from the view and updates the grid accordingly, and also provides the grid's updated state to the view when requested.
 *
 *  @author Adnan Mathuschan
 *  @version 1.0
 *  @since 2023-01-05
 */

package com.example.morpionsolitaire.controllers;
import com.example.morpionsolitaire.db.ScoreDataAccessObject;
import com.example.morpionsolitaire.models.Grid;
import com.example.morpionsolitaire.models.Grid5D;
import com.example.morpionsolitaire.models.Grid5T;
import com.example.morpionsolitaire.models.Link;
import com.example.morpionsolitaire.models.Score;
import com.example.morpionsolitaire.views.GameBoardView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GameController implements GameBoardView.GameBoardListener{

    private GameBoardView viewController;
    private Grid grid;

    private int gameMode;
    private Score score = new Score();

    /**
     * Constructs a new GameController with the given view controller.
     *
     * @param _viewController The view controller for this game.
     * @throws IOException if there is an error reading the saved game data.
     */
    public GameController(GameBoardView _viewController) throws IOException, SQLException {
        this.viewController = _viewController;
        viewController.setGameBoardListener(this);
        Grid.load();
        viewController.initializeCross();
    }

    /**
     * Returns the updated state of the grid.
     *
     * @return The updated grid.
     */
    @Override
    public Grid getUpdatedGrid() {
        return grid;
    }

    /**
     * Reverts the last move made in the game.
     */
     @Override
    public void undo(){
        this.grid.undoLastMove();

    }

    /**
     * Returns a list of all moves made in the current game.
     *
     * @return A list of Link objects representing the moves made in the game.
     */
    @Override
    public List<Link> getHistory() {
        return grid.getMovesHistory();
    }

    /**
     * Initializes a new game of the specified type.
     *
     * @param gameType The type of game to start. This should be either GameBoardView.GAME_5D or GameBoardView.GAME_5T.
     * @throws IOException if there is an error reading the saved game data.
     */
    @Override
    public void startGame(int gameType) throws IOException {
        this.gameMode = gameType;
        if (gameType == GameBoardView.GAME_5D){
            grid = new Grid5D();
        }else{
            grid = new Grid5T();
        }
    }

    @Override
    public int getScoreValue() {
        return this.grid.getScoreValue();
    }

    /**
     * Returns a list of Link objects representing the valid moves that can be made from the given cell.
     *
     * @param i The row index of the cell to check.
     * @param j The column index of the cell to check.
     * @return A list of Link objects representing the valid moves that can be made from the given cell.
     */
    @Override
    public List<Link> canLink(int i, int j) {
        return this.grid.canLink(i,j);
    }

    @Override
    public void resetCell(int i, int j) {
        this.grid.resetCell(i , j );
    }

    /**
     * Makes the specified move on the grid.
     *
     * @param i The row index of the cell to mark.
     * @param j The column index of the cell to mark.
     */
    @Override
    public void playMove(int i, int j) {
        this.grid.playMove(i,j);

    }
    @Override
    public void playRandom(int i, int j){
        this.grid.playRandom(i,j);
    }


    /**
     * Sets the value of the given cell on the grid.
     *
     * @param i The row index of the cell to set.
     * @param j The column index of the cell to set.
     * @param val The value to set the cell to.
     */
    @Override
    public void setCell(int i, int j, int val) {
        this.grid.setCell(i,j,val);
    }


    /**
     * Returns the value of the given cell on the grid.
     *
     * @param i The row index of the cell to get.
     * @param j The column index of the cell to get.
     * @return The value of the given cell.
     */
    @Override
    public int getCell(int i, int j) {
        return Grid.getCell(i,j).getValue();
    }
    @Override
    public int getHighScore() throws SQLException {
        ScoreDataAccessObject dao = new ScoreDataAccessObject();
        Score s = dao.getHighScore();
        if (s != null){
            return s.getValue();
        }
        return 0;
    }

    @Override
    public List<Link> getAllPossibleLinks(){
        return this.grid.getAllPossibleMoves();
    }

    @Override
    public List<Link> getRandomSenario() {
        return grid.getRandomSenario();
    }

    @Override
    public void resetGrid() throws IOException {
        if (this.gameMode == GameBoardView.GAME_5D){
            grid = new Grid5D();
        }else{
            grid = new Grid5T();
        }

    }

    @Override
    public void insertScore(Score s) throws SQLException {
        ScoreDataAccessObject dao = new ScoreDataAccessObject();
        dao.insert(s);
    }

    public void NmcsAlgorithm(Grid state, int level){

        if (level==0){
            return;
        }

    }

}
