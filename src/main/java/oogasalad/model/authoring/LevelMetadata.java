package oogasalad.model.authoring;

public record LevelMetadata(String name, String description, String author) {

    // Factory method to create a new instance of LevelMetadata with updated values
    public LevelMetadata withName(String name) {
        return new LevelMetadata(name, this.description(), this.author());
    }

    public LevelMetadata withDescription(String description) {
        return new LevelMetadata(this.name(), description, this.author());
    }

    public LevelMetadata withAuthor(String author) {
        return new LevelMetadata(this.name(), this.description(), author);
    }

}