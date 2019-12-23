package com.epam.library.controller.command;

public class CommandResult {

    private String page;
    private CommandAction commandAction = CommandAction.FORWARD;


    public CommandResult() {
    }

    public CommandResult(String page) {

        this.page = page;
    }


    public String getPage() {
        return page;
    }

    private void setPage(String page) {
        this.page = page;
    }

    public CommandAction getCommandAction() {
        return commandAction;
    }

    public void redirect(String page) {
        setPage(page);
        commandAction = CommandAction.REDIRECT;
    }

    public void forward(String page) {
        setPage(page);
        commandAction = CommandAction.FORWARD;
    }
}
