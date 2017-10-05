class CreateRooms < ActiveRecord::Migration[5.1]
  def change
    create_table :rooms do |t|
      t.string :room_type
      t.integer :room_num

      t.timestamps
    end
  end
end
