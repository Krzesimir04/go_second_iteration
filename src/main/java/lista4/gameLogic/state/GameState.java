package lista4.gameLogic.state;

public enum GameState {
    WAITING_FOR_GAMERS {
        @Override
        public GameStateBehaviour getStateBehaviour() {
            return new WaitingState();
        }
    },
    GAME_RUNNING {
        @Override
        public GameStateBehaviour getStateBehaviour() {
            return new RunningState();
        }
    };

    /**
     * Default
     * 
     * @return
     */
    public GameStateBehaviour getStateBehaviour() {
        return null;
    }
}
