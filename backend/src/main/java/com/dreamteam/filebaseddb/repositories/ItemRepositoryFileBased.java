package com.dreamteam.filebaseddb.repositories;

import com.dreamteam.filebaseddb.entities.Item;
import com.dreamteam.filebaseddb.exceptions.IllegalItemFormatException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepositoryFileBased implements ItemRepository {
    private static final String databaseName = "D:\\Programs\\IntelliJIDEA\\filebased-db\\backend\\src\\main\\resources\\db\\%s.csv";
    private static final String cacheName = "D:\\Programs\\IntelliJIDEA\\filebased-db\\backend\\src\\main\\resources\\db\\temp.csv";

    private static final String databaseIdIndex = "D:\\Programs\\IntelliJIDEA\\filebased-db\\backend\\src\\main\\resources\\db\\%s_id.index";

    private void writeCacheToDatabase(File database, File cache) {
        try {
            if (!database.delete()) {
                throw new IOException("Can not delete file");
            }
            if (!cache.renameTo(database)) {
                throw new IOException("Can not rename file");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }
    }

    private void buildIndex(File database, File databaseIdIndexFile) {
        try (BufferedReader databaseReader = new BufferedReader(new FileReader(database));
             ObjectOutputStream indexWriter = new ObjectOutputStream(new FileOutputStream(databaseIdIndexFile))) {

            AtomicLong currentLine = new AtomicLong();
            Map<Long, Long> index = new TreeMap<>(); // uses tree inside for storing data

            databaseReader.lines()
                    .forEach(line -> {
                        String[] data = line.split(",");
                        Item item = Item.deserialize(data);

                        index.put(item.getId(), currentLine.getAndIncrement());
                    });

            indexWriter.writeObject(index);

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }
    }


    public List<Item> findAll() {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));
        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));
        buildIndex(database, databaseIdIndexFile); // rebuild index whenever all items requested

        List<Item> items = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(database))) {
            reader.lines()
                    .forEach(line -> {
                        String[] data = line.split(",");
                        items.add(Item.deserialize(data));
                    });
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }

        return items;
    }

    public void save(Item item) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(database, true))) {
            String row = String.join(",", item.serialize());
            writer.write(row);
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalItemFormatException(e.getMessage(), e);
        }

        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));
        buildIndex(database, databaseIdIndexFile); // rebuild index
    }


    public Optional<Item> findItemById(Long id) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));
        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));

        return findItemById(id, database, databaseIdIndexFile);
    }

    private Optional<Item> findItemById(Long id, File database, File databaseIndexFile) {
        try (BufferedReader databaseReader = new BufferedReader(new FileReader(database));
             ObjectInputStream indexReader = new ObjectInputStream(new FileInputStream(databaseIndexFile))) {

            Map<Long, Long> index = (TreeMap<Long, Long>) indexReader.readObject();

            if (index.containsKey(id)) {
                long lineOfItem = index.get(id);

                Optional<String> dataOpt = databaseReader.lines().skip(lineOfItem).findFirst();

                if (dataOpt.isPresent()) {
                    String[] data = dataOpt.get().split(",");
                    return Optional.of(Item.deserialize(data));
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.empty();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }
    }


    public List<Item> findAllItemsById(Long id) {
        return findItemById(id).map(List::of).orElseGet(List::of);
    }

    public List<Item> findAllItemsByName(String name) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));

        List<Item> items = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(database))) {
            reader.lines()
                    .forEach(line -> {
                        String[] data = line.split(",");
                        Item item = Item.deserialize(data);

                        if (item.getName().equalsIgnoreCase(name)) {
                            items.add(item);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }

        return items;
    }

    public List<Item> findAllItemsByAmountAvailable(Long amountAvailable) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));

        List<Item> items = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(database))) {
            reader.lines()
                    .forEach(line -> {
                        String[] data = line.split(",");
                        Item item = Item.deserialize(data);

                        if (item.getAmountAvailable().equals(amountAvailable)) {
                            items.add(item);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }

        return items;
    }

    public List<Item> findAllItemsByPrice(Integer price) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));

        List<Item> items = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(database))) {
            reader.lines()
                    .forEach(line -> {
                        String[] data = line.split(",");
                        Item item = Item.deserialize(data);

                        if (item.getPrice().equals(price)) {
                            items.add(item);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }

        return items;
    }

    public List<Item> findAllItemsByColor(String color) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));

        List<Item> items = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(database))) {
            reader.lines()
                    .forEach(line -> {
                        String[] data = line.split(",");
                        Item item = Item.deserialize(data);

                        if (item.getColor().equalsIgnoreCase(color)) {
                            items.add(item);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }

        return items;
    }

    public List<Item> findAllItemsByRefurbished(Boolean refurbished) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));

        List<Item> items = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(database))) {
            reader.lines()
                    .forEach(line -> {
                        String[] data = line.split(",");
                        Item item = Item.deserialize(data);

                        if (item.getRefurbished().equals(refurbished)) {
                            items.add(item);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }

        return items;
    }


    public void deleteById(Long id) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));
        File cache = new File(cacheName);

        try (BufferedReader reader = new BufferedReader(new FileReader(database));
             BufferedWriter writer = new BufferedWriter(new FileWriter(cache))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Item item = Item.deserialize(data);

                if (!item.getId().equals(id)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }
        writeCacheToDatabase(database, cache);

        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));
        buildIndex(database, databaseIdIndexFile); // rebuild index
    }

    public void deleteAllItemsByName(String name) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));
        File cache = new File(cacheName);

        try (BufferedReader reader = new BufferedReader(new FileReader(database));
             BufferedWriter writer = new BufferedWriter(new FileWriter(cache))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                Item item = Item.deserialize(data);

                if (!item.getName().equalsIgnoreCase(name)) {
                    writer.write(row);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }
        writeCacheToDatabase(database, cache);

        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));
        buildIndex(database, databaseIdIndexFile); // rebuild index
    }

    public void deleteAllItemsByAmountAvailable(Long amountAvailable) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));
        File cache = new File(cacheName);

        try (BufferedReader reader = new BufferedReader(new FileReader(database));
             BufferedWriter writer = new BufferedWriter(new FileWriter(cache))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                Item item = Item.deserialize(data);

                if (!item.getAmountAvailable().equals(amountAvailable)) {
                    writer.write(row);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }
        writeCacheToDatabase(database, cache);

        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));
        buildIndex(database, databaseIdIndexFile); // rebuild index
    }

    public void deleteAllItemsByPrice(Integer price) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));
        File cache = new File(cacheName);

        try (BufferedReader reader = new BufferedReader(new FileReader(database));
             BufferedWriter writer = new BufferedWriter(new FileWriter(cache))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                Item item = Item.deserialize(data);

                if (!item.getPrice().equals(price)) {
                    writer.write(row);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }
        writeCacheToDatabase(database, cache);

        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));
        buildIndex(database, databaseIdIndexFile); // rebuild index
    }

    public void deleteAllItemsByColor(String color) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));
        File cache = new File(cacheName);

        try (BufferedReader reader = new BufferedReader(new FileReader(database));
             BufferedWriter writer = new BufferedWriter(new FileWriter(cache))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                Item item = Item.deserialize(data);

                if (!item.getColor().equalsIgnoreCase(color)) {
                    writer.write(row);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }
        writeCacheToDatabase(database, cache);

        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));
        buildIndex(database, databaseIdIndexFile); // rebuild index
    }

    public void deleteAllItemsByRefurbished(Boolean refurbished) {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));
        File cache = new File(cacheName);

        try (BufferedReader reader = new BufferedReader(new FileReader(database));
             BufferedWriter writer = new BufferedWriter(new FileWriter(cache))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                Item item = Item.deserialize(data);

                if (!item.getRefurbished().equals(refurbished)) {
                    writer.write(row);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }
        writeCacheToDatabase(database, cache);

        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));
        buildIndex(database, databaseIdIndexFile); // rebuild index
    }


    public void deleteAllItems() {
        File database = new File(databaseName.formatted(System.getProperty("databaseName")));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(database))) {
            // just opening for writing: it clears file
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalItemFormatException(e.getMessage(), e);
        }

        File databaseIdIndexFile = new File(databaseIdIndex.formatted(System.getProperty("databaseName")));
        buildIndex(database, databaseIdIndexFile); // rebuild index
    }
}
