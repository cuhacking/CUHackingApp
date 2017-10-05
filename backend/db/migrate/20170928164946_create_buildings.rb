class CreateBuildings < ActiveRecord::Migration[5.1]
  def change
    create_table :buildings do |t|
      t.has_many :room
      t.string :name
      t.string :initials

      t.timestamps
    end
  end
end
