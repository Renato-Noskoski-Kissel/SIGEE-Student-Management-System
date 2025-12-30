package Model.patterns;

public interface ISubject {
    void notifyObservers(String message);
    void addObserver(IObserver observer);
    void removeObserver(IObserver observer);
}
