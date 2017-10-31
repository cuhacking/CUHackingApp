class CreateRooms < ActiveRecord::Migration[5.1]
  def change
    create_table :rooms do |t|
      t.belongs_to :event, optional: true
      t.belongs_to :building, optional: true
      
      t.string :type
      t.integer :name

      t.timestamps
    end
  end
end
